# código que crea el ambiente para chomp champs

import argparse
import os
import time
import random


# class Player:
#     def __init__(self, player_number):


class GameState:
    def __init__(self, width=5, height=5, num_players=2):
        self.width = width
        self.height = height
        self.num_players = num_players
        self.players = []
        self.board = []
        self.reset()


    def start(self):
        """
        Ciclo principal del juego, simula los pipes (obligando a jugar con 0-7 o pasar con 8)
        """
        game_finished = False
        
        self.reset()

        while !game_finished:
            

    def reset(self):
        """
        Inicializa el tablero y ubica jugadores aleatoriamente
        """
        self.board = [[random.randint(1, 9) for _ in range(self.width)] for _ in range(self.height)]
        self.players = []
        positions = set()
        for p in range(self.num_players):
            while True:
                x = random.randint(0, self.width - 1)
                y = random.randint(0, self.height - 1)
                if (x, y) not in positions:
                    positions.add((x, y))
                    self.players.append({"id": p, "x": x, "y": y})
                    self.board[y][x] = -p
                    break


    def render(self):
        """
        Dibuja el tablero en la terminal
        """
        os.system("cls" if os.name == "nt" else "clear")
        for row in self.board:
            print(" ".join(row))
        print()


    def turn(self, actions):
        """
        Avanza un turno
        `actions`: lista con la acción de cada jugador (ej: ["up", "stay"])
        """
        # limpiar posiciones viejas
        self.board = [["." for _ in range(self.width)] for _ in range(self.height)]
        
        for i, player in enumerate(self.players):
            action = actions[i]
            dx, dy = 0, 0
            if action == "up":
                dy = -1
            elif action == "down":
                dy = 1
            elif action == "left":
                dx = -1
            elif action == "right":
                dx = 1
            # "stay" significa no moverse

            new_x = max(0, min(self.width - 1, player["x"] + dx))
            new_y = max(0, min(self.height - 1, player["y"] + dy))

            # actualizar jugador
            player["x"], player["y"] = new_x, new_y

        # volver a dibujar los jugadores
        for player in self.players:
            self.board[player["y"]][player["x"]] = f"P{player['id']}"




# 



# ==== main ====
def main():
    # procesamiento de argumentos
    ap = argparse.ArgumentParser(description="ChompChamps game simulator")
    ap.add_argument("--width", type=int, default=10)
    ap.add_argument("--height", type=int, default=10)
    ap.add_argument("--players", type=int, default=2)
    ap.add_argument("--delay", type=int, default=200)
    args = ap.parse_args()


    game = GameState(args.width, args.height, args.delay, args.players)

    game.start()






if __name__ == "__main__":
    main()