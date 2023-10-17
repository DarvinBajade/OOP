import pygame
import spritesheet

#Bajade, Darvin Kobe M.
#BSCS 3-1

pygame.init()

SCREEN_WIDTH = 1000
SCREEN_HEIGHT = 500

clock = pygame.time.Clock()

screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))
pygame.display.set_caption('PT 1')

background_image = pygame.image.load('background.png').convert()
background_image = pygame.transform.scale(background_image,(SCREEN_WIDTH, SCREEN_HEIGHT))

sprite_sheet_image = pygame.image.load('DinoSprites - doux.png').convert_alpha()
sprite_sheet_mirror_image = pygame.image.load('DinoSprites - doux(mirror).png').convert_alpha()

sprite_sheet = spritesheet.SpriteSheet(sprite_sheet_image)
sprite_sheet_mirror = spritesheet.SpriteSheet(sprite_sheet_mirror_image)

BG = (50, 50, 50)
BLACK = (0, 0, 0)

frames = [
    sprite_sheet.get_image(0, 24, 24, 5, BLACK),
    sprite_sheet.get_image(1, 24, 24, 5, BLACK),
    sprite_sheet.get_image(2, 24, 24, 5, BLACK),
    sprite_sheet.get_image(3, 24, 24, 5, BLACK),
    sprite_sheet.get_image(4, 24, 24, 5, BLACK),
    sprite_sheet.get_image(5, 24, 24, 5, BLACK),
    sprite_sheet.get_image(6, 24, 24, 5, BLACK)
]

frames_mirror = [
    sprite_sheet_mirror.get_image(23, 24, 24, 5, BLACK),
    sprite_sheet_mirror.get_image(22, 24, 24, 5, BLACK),
    sprite_sheet_mirror.get_image(21, 24, 24, 5, BLACK),
    sprite_sheet_mirror.get_image(20, 24, 24, 5, BLACK),
    sprite_sheet_mirror.get_image(19, 24, 24, 5, BLACK),
    sprite_sheet_mirror.get_image(18, 24, 24, 5, BLACK),
    sprite_sheet_mirror.get_image(17, 24, 24, 5, BLACK)
]

frame_index = 0
frame_timer = 0

x = SCREEN_WIDTH // 2
y = SCREEN_HEIGHT // 2
velocity_x = 5
velocity_y = 0
jumping = False
jump_height = 100
facing_left = False

run = True
while run:
  clock.tick(60)
  for event in pygame.event.get():
    if event.type == pygame.QUIT:
      run = False

  keys = pygame.key.get_pressed()

  if keys[pygame.K_UP] and not jumping:
    jumping = True
    velocity_y = -10

  if jumping:
    y += velocity_y
    velocity_y += 0.5

  if y >= SCREEN_HEIGHT - frames[frame_index].get_height():
    y = SCREEN_HEIGHT - frames[frame_index].get_height()
    jumping = False
    velocity_y = 0

  if keys[pygame.K_LEFT]:
    x -= velocity_x
    facing_left = True
  elif keys[pygame.K_RIGHT]:
    x += velocity_x
    facing_left = False

  x = max(0, min(SCREEN_WIDTH - frames[frame_index].get_width(), x))

  screen.blit(background_image, (0, 0))

  if facing_left:
    screen.blit(frames_mirror[frame_index], (x, y))
  else:
    screen.blit(frames[frame_index], (x, y))

  frame_timer += 1

  if frame_timer >= 10:
    frame_index = (frame_index + 1) % len(frames)
    frame_timer = 0

  pygame.display.update()

pygame.quit()
