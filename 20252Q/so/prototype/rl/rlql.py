#!/usr/bin/env python3
# train_and_emit_c.py
# Entrena Q-learning para ChompChamps y emite C con Q-tables + get_next_move()

import argparse
import random
import numpy as np

# --------- Acciones 0..7: arriba y horario ----------
OFFSETS = np.array([
    (0, -1),   # 0: arriba
    (1, -1),   # 1: arriba-derecha
    (1,  0),   # 2: derecha
    (1,  1),   # 3: abajo-derecha
    (0,  1),   # 4: abajo
    (-1, 1),   # 5: abajo-izquierda
    (-1, 0),   # 6: izquierda
    (-1,-1),   # 7: arriba-izquierda
], dtype=np.int32)

# --------- Entorno simplificado turno a turno ----------
class Player:
    def __init__(self, pid, x, y):
        self.id = pid            # 1..N
        self.x = x
        self.y = y
        self.score = 0
        self.invalid = 0

class Env:
    def __init__(self, width, height, n_players, seed=None):
        self.width = width
        self.height = height
        self.n_players = n_players
        self.rng = np.random.default_rng(seed)
        self.reset()

    def reset(self):
        # Recompensas 1..9
        self.board = self.rng.integers(1, 10, size=(self.height, self.width), dtype=np.int16)
        self.players = []
        taken = set()
        for pid in range(1, self.n_players+1):
            while True:
                x = int(self.rng.integers(0, self.width))
                y = int(self.rng.integers(0, self.height))
                if (x,y) not in taken and self.board[y, x] > 0:
                    self.players.append(Player(pid, x, y))
                    self.board[y, x] = -pid
                    taken.add((x,y))
                    break
        self.no_valid_streak = 0
        return self._obs()

    def _obs(self):
        # Para Q-learning tabular por (x,y), el estado de cada jugador es solo su pos actual.
        # (El resto del tablero afecta legalidad/reward pero no indexamos el estado con eso.)
        return [(p.x, p.y) for p in self.players]

    def in_bounds(self, x, y):
        return 0 <= x < self.width and 0 <= y < self.height

    def legal(self, x, y):
        return self.in_bounds(x,y) and (self.board[y, x] > 0)

    def step_player(self, p: Player, a: int, invalid_penalty: float):
        if a < 0 or a > 7:
            p.invalid += 1
            return False, -abs(invalid_penalty)
        dx, dy = OFFSETS[a]
        nx, ny = p.x + int(dx), p.y + int(dy)
        if not self.in_bounds(nx, ny) or self.board[ny, nx] <= 0:
            p.invalid += 1
            return False, -abs(invalid_penalty)
        # válido
        reward = int(self.board[ny, nx])
        p.score += reward
        self.board[ny, nx] = -p.id
        p.x, p.y = nx, ny
        return True, reward

    def any_moves_left(self):
        for p in self.players:
            for dx, dy in OFFSETS:
                nx, ny = p.x + int(dx), p.y + int(dy)
                if self.legal(nx, ny):
                    return True
        return False

    def step_round(self, actions, invalid_penalty):
        """
        actions: list[int] tamaño n_players, cada uno ∈ [0..7]
        Devuelve: rewards list[float], valids list[bool]
        """
        rewards = []
        valids = []
        any_valid = False
        for p, a in zip(self.players, actions):
            ok, r = self.step_player(p, a, invalid_penalty)
            rewards.append(r)
            valids.append(ok)
            if ok:
                any_valid = True
        if any_valid:
            self.no_valid_streak = 0
        else:
            self.no_valid_streak += 1
        done = (not self.any_moves_left()) or (self.no_valid_streak >= 20)
        return self._obs(), rewards, done, valids

# --------- Políticas de otros jugadores (heurísticas/ruido) ----------
def greedy_neighbor_action(env: Env, p: Player):
    best = None
    best_val = -10
    for a in range(8):
        dx, dy = OFFSETS[a]
        nx, ny = p.x + int(dx), p.y + int(dy)
        if env.in_bounds(nx, ny):
            v = env.board[ny, nx]
            if v > best_val and v > 0:
                best_val = v
                best = a
    return best if best is not None else 0

def noisy_greedy_action(env: Env, p: Player, eps=0.1):
    if random.random() < eps:
        return random.randint(0,7)
    return greedy_neighbor_action(env, p)

# --------- Q-learning ----------
def train_q(width, height, n_players, episodes=2000, alpha=0.1, gamma=0.95,
            eps_start=0.3, eps_end=0.05, invalid_penalty=0.2, seed=123):
    rng = np.random.default_rng(seed)
    env = Env(width, height, n_players, seed=seed)

    # Una Q por jugador: (width*height, 8)
    Qs = [np.zeros((width*height, 8), dtype=np.float32) for _ in range(n_players)]

    def sidx(x, y): return y*width + x

    for ep in range(episodes):
        env.reset()
        # decaimiento epsilon lineal
        eps = eps_end + (eps_start - eps_end) * max(0, (episodes-ep)/episodes)

        done = False
        while not done:
            actions = []
            for j, p in enumerate(env.players):
                # jug #0 y #1 (si existen) usan sus Q; el resto heurística
                if j < len(Qs):
                    if rng.random() < eps:
                        a = rng.integers(0,8)
                    else:
                        a = int(np.argmax(Qs[j][sidx(p.x, p.y)]))
                else:
                    a = noisy_greedy_action(env, p, eps=0.2)
                actions.append(int(a))

            prev_positions = [(p.x, p.y) for p in env.players]
            _, rewards, done, valids = env.step_round(actions, invalid_penalty)

            # Q-update para jugadores con Q
            for j, p in enumerate(env.players[:len(Qs)]):
                x0, y0 = prev_positions[j]
                a = actions[j]
                r = rewards[j]
                s0 = sidx(x0, y0)
                s1 = sidx(p.x, p.y)
                td_target = r + (0.0 if done else gamma * np.max(Qs[j][s1]))
                Qs[j][s0, a] += alpha * (td_target - Qs[j][s0, a])

    return Qs

# --------- Emisión de C ----------
C_HEADER = r"""#include <stdbool.h>
#include <stdint.h>
#define NUM_ACTIONS 8

// NOTA: Estas tablas fueron entrenadas para WIDTH x HEIGHT fijos.
// Si el tamaño en runtime no coincide, se cae a un plan B (greedy) seguro.
"""

C_FUNC = r"""
// Greedy de respaldo si el tamaño en runtime no coincide con el entrenado
static inline int fallback_greedy(const int *board, int width, int height, int x, int y) {
    int best_a = 0;
    int best_v = -10;
    const int dx[8] = {0, 1, 1, 1, 0,-1,-1,-1};
    const int dy[8] = {-1,-1, 0, 1, 1, 1, 0,-1};
    for (int a=0; a<8; ++a) {
        int nx = x + dx[a], ny = y + dy[a];
        if (nx>=0 && nx<width && ny>=0 && ny<height) {
            int v = board[ny*width + nx];
            if (v > 0 && v > best_v) { best_v = v; best_a = a; }
        }
    }
    return best_a;
}

int get_next_move(Tgame_state *game_state, int player_number) {
    int width = game_state->width;
    int height = game_state->height;
    int x = game_state->players[player_number].x;
    int y = game_state->players[player_number].y;

    // Si el tamaño no coincide, usar greedy de respaldo (seguro y no traba el juego)
    if (width != WIDTH || height != HEIGHT) {
        return fallback_greedy(game_state->board, width, height, x, y);
    }

    // Usar Q del jugador si existe, si no, greedy
    int state_index = y * WIDTH + x;
    const int dx[8] = {0, 1, 1, 1, 0,-1,-1,-1};
    const int dy[8] = {-1,-1, 0, 1, 1, 1, 0,-1};

    int best_action = -1;
    int32_t best_value = INT32_MIN;

    // Seleccionar tabla Q correspondiente
    const int32_t (*Q)[NUM_ACTIONS] = NULL;
    switch (player_number) {
        PLAYER_Q_SWITCH_CASES
        default: Q = NULL; break;
    }

    if (!Q) {
        return fallback_greedy(game_state->board, width, height, x, y);
    }

    // Elegir SOLO entre acciones válidas (dentro de tablero y board>0)
    for (int a=0; a<NUM_ACTIONS; ++a) {
        int nx = x + dx[a];
        int ny = y + dy[a];
        if (nx>=0 && nx<width && ny>=0 && ny<height) {
            int cell = game_state->board[ny*width + nx];
            if (cell > 0) {
                int32_t val = Q[state_index][a];
                if (val > best_value) { best_value = val; best_action = a; }
            }
        }
    }

    if (best_action >= 0) return best_action;

    // Si no hay ninguna válida según Q, usar greedy de respaldo
    return fallback_greedy(game_state->board, width, height, x, y);
}
"""

def emit_c(Qs, width, height, scale=1.0):
    # Escalamos y convertimos a int para C
    Qis = [np.round(Q * scale).astype(np.int32) for Q in Qs]
    n_players = len(Qis)

    parts = [C_HEADER]
    parts.append(f"#define WIDTH {width}\n#define HEIGHT {height}\n")

    # Emitir tablas
    for pid, Qi in enumerate(Qis):
        parts.append(f"static const int32_t Q_player{pid}[{width*height}][NUM_ACTIONS] = {{\n")
        for s in range(width*height):
            row = ", ".join(str(int(v)) for v in Qi[s])
            parts.append(f"  {{{row}}},\n")
        parts.append("};\n\n")

    # switch-case para seleccionar Q
    cases = []
    for pid in range(n_players):
        cases.append(f"        case {pid}: Q = Q_player{pid}; break;")
    switch_cases = "\n".join(cases)

    func = C_FUNC.replace("PLAYER_Q_SWITCH_CASES", switch_cases)
    parts.append(func)
    return "".join(parts)

# --------- Main ----------
def main():
    ap = argparse.ArgumentParser(description="Q-learning trainer y emisor de C para get_next_move()")
    ap.add_argument("--width", type=int, default=10)
    ap.add_argument("--height", type=int, default=10)
    ap.add_argument("--players", type=int, default=2)
    ap.add_argument("--episodes", type=int, default=4000)
    ap.add_argument("--alpha", type=float, default=0.1)
    ap.add_argument("--gamma", type=float, default=0.95)
    ap.add_argument("--eps_start", type=float, default=0.3)
    ap.add_argument("--eps_end", type=float, default=0.05)
    ap.add_argument("--invalid_penalty", type=float, default=0.2)
    ap.add_argument("--seed", type=int, default=123)
    ap.add_argument("--scale", type=float, default=1.0, help="Escala para convertir Q a enteros (p.ej. 1000)")
    args = ap.parse_args()

    Qs = train_q(
        width=args.width, height=args.height, n_players=args.players,
        episodes=args.episodes, alpha=args.alpha, gamma=args.gamma,
        eps_start=args.eps_start, eps_end=args.eps_end,
        invalid_penalty=args.invalid_penalty, seed=args.seed
    )

    c_code = emit_c(Qs, args.width, args.height, scale=args.scale)
    print(c_code)

if __name__ == "__main__":
    main()



# solamente genera para ciertos parámetros específicos, sino usa plan b
# --episodes cantidad de simulaciones
# python3 rlql.py --width 20 --height 20 --players 2 --episodes 6000 --scale 100