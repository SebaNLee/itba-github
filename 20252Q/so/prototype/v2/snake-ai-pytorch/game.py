import pygame
import random
from enum import Enum
from collections import namedtuple
import numpy as np

pygame.init()
font = pygame.font.Font('arial.ttf', 25)

class Direction(Enum):
    RIGHT = 1
    LEFT = 2
    UP = 3
    DOWN = 4
    UP_LEFT = 5
    UP_RIGHT = 6
    DOWN_LEFT = 7
    DOWN_RIGHT = 8

Point = namedtuple('Point', 'x, y')

# colores
WHITE = (255, 255, 255)
RED = (200,0,0)
BLUE1 = (0, 0, 255)
BLACK = (0,0,0)

BLOCK_SIZE = 20
SPEED = 150

class ChompChamps:

    def __init__(self, w=400, h=400):
        self.w = w
        self.h = h
        self.cols = w // BLOCK_SIZE
        self.rows = h // BLOCK_SIZE

        # init display
        self.display = pygame.display.set_mode((self.w, self.h))
        pygame.display.set_caption('ChompChamps')
        self.clock = pygame.time.Clock()

        self.reset()

    def reset(self):
        # posición inicial en el centro
        self.player = Point(self.cols//2, self.rows//2)
        self.score = 0
        self.valid_moves = 0
        self.invalid_moves = 0
        self.frame_iteration = 0

        # grilla con recompensas 1-9
        self.grid = np.random.randint(1, 10, size=(self.rows, self.cols))

        # la celda inicial no da puntos y queda ocupada
        self.grid[self.player.y, self.player.x] = 0

    def play_step(self, action):
        self.frame_iteration += 1

        # eventos quit
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                quit()

        # 1. mover jugador según acción
        old_pos = self.player
        new_pos = self._move(action)

        # 2. verificar validez del movimiento
        if self._is_valid(new_pos):
            self.player = new_pos
            gained = self.grid[new_pos.y, new_pos.x]
            self.score += gained
            self.grid[new_pos.y, new_pos.x] = 0
            reward = gained
            self.valid_moves += 1
        else:
            reward = -1
            self.invalid_moves += 1

        # 3. condición de fin (sin celdas libres alrededor)
        if not self._has_moves():
            return reward, True, self.score

        # 4. dibujar
        self._update_ui()
        self.clock.tick(SPEED)

        return reward, False, self.score

    def _is_valid(self, pos):
        # adentro del tablero
        if pos.x < 0 or pos.x >= self.cols or pos.y < 0 or pos.y >= self.rows:
            return False
        # celda ya capturada
        if self.grid[pos.y, pos.x] == 0:
            return False
        return True

    def _has_moves(self):
        for dx in [-1,0,1]:
            for dy in [-1,0,1]:
                if dx == 0 and dy == 0:
                    continue
                if self._is_valid(Point(self.player.x+dx, self.player.y+dy)):
                    return True
        return False

    def _update_ui(self):
        self.display.fill(BLACK)

        # dibujar grilla
        for y in range(self.rows):
            for x in range(self.cols):
                val = self.grid[y, x]
                if val > 0:
                    color = (50+val*20, 50, 50)  # intensidad según recompensa
                    pygame.draw.rect(self.display, color,
                        pygame.Rect(x*BLOCK_SIZE, y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE))

        # jugador
        pygame.draw.rect(self.display, BLUE1,
            pygame.Rect(self.player.x*BLOCK_SIZE, self.player.y*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE))

        # puntaje
        text = font.render(f"Score: {self.score}", True, WHITE)
        self.display.blit(text, [0, 0])

        pygame.display.flip()

    def _move(self, action):
        # acción = np.array one-hot de 8 direcciones
        directions = [
            Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT,
            Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT
        ]
        idx = np.argmax(action)
        dir = directions[idx]

        x, y = self.player.x, self.player.y
        if dir == Direction.UP: y -= 1
        elif dir == Direction.DOWN: y += 1
        elif dir == Direction.LEFT: x -= 1
        elif dir == Direction.RIGHT: x += 1
        elif dir == Direction.UP_LEFT: x -= 1; y -= 1
        elif dir == Direction.UP_RIGHT: x += 1; y -= 1
        elif dir == Direction.DOWN_LEFT: x -= 1; y += 1
        elif dir == Direction.DOWN_RIGHT: x += 1; y += 1

        return Point(x, y)
