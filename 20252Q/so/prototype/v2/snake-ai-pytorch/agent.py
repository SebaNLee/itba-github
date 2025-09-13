import torch
import random
import numpy as np
from collections import deque
from game import ChompChamps, Point
from model import Linear_QNet, QTrainer
from helper import plot

MAX_MEMORY = 100_000
BATCH_SIZE = 1000
LR = 0.001

class Agent:

    def __init__(self):
        self.n_games = 0
        self.epsilon = 0
        self.gamma = 0.9
        self.memory = deque(maxlen=MAX_MEMORY)
        # 16 entradas (8 validez + 8 recompensas), salida = 8 acciones
        self.model = Linear_QNet(16, 256, 8)
        self.trainer = QTrainer(self.model, lr=LR, gamma=self.gamma)

    def get_state(self, game: ChompChamps):
        x, y = game.player.x, game.player.y

        directions = [
            Point(x, y-1),   # UP
            Point(x, y+1),   # DOWN
            Point(x-1, y),   # LEFT
            Point(x+1, y),   # RIGHT
            Point(x-1, y-1), # UP_LEFT
            Point(x+1, y-1), # UP_RIGHT
            Point(x-1, y+1), # DOWN_LEFT
            Point(x+1, y+1)  # DOWN_RIGHT
        ]

        state = []
        for pos in directions:
            # válido o no
            valid = game._is_valid(pos)
            state.append(int(valid))
        for pos in directions:
            if game._is_valid(pos):
                reward_val = game.grid[pos.y, pos.x] / 9.0  # normalizado [0,1]
            else:
                reward_val = 0.0
            state.append(reward_val)

        return np.array(state, dtype=float)

    def remember(self, state, action, reward, next_state, done):
        self.memory.append((state, action, reward, next_state, done))

    def train_long_memory(self):
        if len(self.memory) > BATCH_SIZE:
            mini_sample = random.sample(self.memory, BATCH_SIZE)
        else:
            mini_sample = self.memory
        states, actions, rewards, next_states, dones = zip(*mini_sample)
        self.trainer.train_step(states, actions, rewards, next_states, dones)

    def train_short_memory(self, state, action, reward, next_state, done):
        self.trainer.train_step(state, action, reward, next_state, done)

    def get_action(self, state):
        self.epsilon = 80 - self.n_games
        final_move = [0]*8
        if random.randint(0, 200) < self.epsilon:
            move = random.randint(0, 7)
        else:
            state0 = torch.tensor(state, dtype=torch.float).to("cpu")
            prediction = self.model(state0)
            move = torch.argmax(prediction).item()
        final_move[move] = 1
        return final_move


def train():
    plot_scores = []
    plot_mean_scores = []
    total_score = 0
    record = 0
    agent = Agent()
    game = ChompChamps()
    while True:
        state_old = agent.get_state(game)

        final_move = agent.get_action(state_old)

        reward, done, score = game.play_step(final_move)
        state_new = agent.get_state(game)

        agent.train_short_memory(state_old, final_move, reward, state_new, done)

        agent.remember(state_old, final_move, reward, state_new, done)

        if done:
            game.reset()
            agent.n_games += 1
            agent.train_long_memory()

            if score > record:
                record = score
                agent.model.save()

            print('Game', agent.n_games, 'Score', score, 'Record:', record)

            plot_scores.append(score)
            total_score += score
            mean_score = total_score / agent.n_games
            plot_mean_scores.append(mean_score)
            plot(plot_scores, plot_mean_scores)


if __name__ == '__main__':
    train()
