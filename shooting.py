import pygame
import spritesheet

# Bajade, Darvin Kobe M.
# BSCS 3-1

pygame.init()

SCREEN_WIDTH = 1000
SCREEN_HEIGHT = 500

screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))
pygame.display.set_caption('OE')

background_image = pygame.image.load('background.png').convert()
background_image = pygame.transform.scale(background_image, (SCREEN_WIDTH, SCREEN_HEIGHT))

sprite_sheet_image = pygame.image.load('Gunner_Black_Idle.png').convert_alpha()
sprite_sheet_image_run = pygame.image.load('Gunner_Black_Run.png').convert_alpha()
sprite_sheet_image_jump = pygame.image.load('Gunner_Black_Jump.png').convert_alpha()
bullet_sprite_image = pygame.image.load('Fire_bullet.png').convert_alpha()

sprite_sheet = spritesheet.SpriteSheet(sprite_sheet_image)
sprite_sheet_run = spritesheet.SpriteSheet(sprite_sheet_image_run)
sprite_sheet_jump = spritesheet.SpriteSheet(sprite_sheet_image_jump)
bullet_sprite_sheet = spritesheet.SpriteSheet(bullet_sprite_image)


BG = (50, 50, 50)
BLACK = (0, 0, 0)

frames_idle = [
    sprite_sheet.get_image(0, 48, 48, 3, BLACK),
    sprite_sheet.get_image(1, 48, 48, 3, BLACK),
    sprite_sheet.get_image(2, 48, 48, 3, BLACK),
    sprite_sheet.get_image(3, 48, 48, 3, BLACK),
    sprite_sheet.get_image(4, 48, 48, 3, BLACK),
]

frames_run = [
    sprite_sheet_run.get_image(0, 48, 48, 3, BLACK),
    sprite_sheet_run.get_image(1, 48, 48, 3, BLACK),
    sprite_sheet_run.get_image(2, 48, 48, 3, BLACK),
    sprite_sheet_run.get_image(3, 48, 48, 3, BLACK),
    sprite_sheet_run.get_image(4, 48, 48, 3, BLACK),
    sprite_sheet_run.get_image(5, 48, 48, 3, BLACK)
]

frames_jump = [
    sprite_sheet_jump.get_image(0, 48, 48, 3, BLACK),
    sprite_sheet_jump.get_image(1, 48, 48, 3, BLACK),
]

bullet_frames = [
    bullet_sprite_sheet.get_image(16, 16, 16, 3, BLACK),
    bullet_sprite_sheet.get_image(17, 16, 16, 3, BLACK),
    bullet_sprite_sheet.get_image(18, 16, 16, 3, BLACK),
    bullet_sprite_sheet.get_image(19, 16, 16, 3, BLACK),
]

projectile_image = pygame.Surface((20, 5))
projectile_image.fill((255, 255, 0))

projectiles = []

clock = pygame.time.Clock()

run = True

frame_index = 0
frame_timer = 0
is_idle = True
is_jumping = False
jump_height = 100
jump_velocity = 10

x = SCREEN_WIDTH // 2
y = SCREEN_HEIGHT - 144
velocity_x = 5

is_left_pressed = False
is_right_pressed = False

jump_frame_index = 0  
shot_interval = 30  # Adjust this value to change the interval (measured in frames)
current_shot_delay = 0  # Initialize the delay timer

while run:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            run = False

    keys = pygame.key.get_pressed()

    screen.blit(background_image, (0, 0))

    if keys[pygame.K_LEFT]:
        is_left_pressed = True
    else:
        is_left_pressed = False

    if keys[pygame.K_RIGHT]:
        is_right_pressed = True
    else:
        is_right_pressed = False

    if is_left_pressed and not is_right_pressed:
        is_idle = False
        x -= velocity_x
        frame_timer += 5
        if frame_timer >= 10:
            frame_index = (frame_index + 1) % len(frames_run)
            frame_timer = 0
        if not is_jumping:
            screen.blit(frames_run[frame_index], (max(0, min(x, SCREEN_WIDTH - 12)), y))
    elif is_right_pressed and not is_left_pressed:
        is_idle = False
        x += velocity_x
        frame_timer += 1
        if frame_timer >= 10:
            frame_index = (frame_index + 1) % len(frames_run)
            frame_timer = 0
        if not is_jumping:
            screen.blit(frames_run[frame_index], (max(0, min(x, SCREEN_WIDTH - 12)), y))
    else:
        is_idle = True
        frame_index = 0
        frame_timer += 0.5
        if frame_timer >= 10:
            frame_index = (frame_index + 1) % len(frames_idle)
            frame_timer = 0
        if not is_jumping:
            screen.blit(frames_idle[frame_index], (max(0, min(x, SCREEN_WIDTH - 12)), y))

    if keys[pygame.K_UP] and not is_jumping:
        is_jumping = True
        jump_start_y = y
        jump_velocity = 10
        jump_frame_index = 0 

    if is_jumping:
        y -= jump_velocity
        jump_velocity -= 1

        if y >= jump_start_y:
            y = jump_start_y
            is_jumping = False

        frame_timer += 1
        if frame_timer >= 10:
            jump_frame_index = (jump_frame_index + 1) % len(frames_jump)
            frame_timer = 0
        screen.blit(frames_jump[jump_frame_index], (max(0, min(x, SCREEN_WIDTH - 12)), y))
    if keys[pygame.K_SPACE] and current_shot_delay <= 0:
        projectile_x = x + 100
        projectile_y = y + 44
        projectiles.append([projectile_x, projectile_y, 0])  # Add a frame index
        current_shot_delay = shot_interval  # Reset the delay timer

    # Update the shot delay timer
    if current_shot_delay > 0:
        current_shot_delay -= 1

    new_projectiles = []
    for projectile in projectiles:
        projectile[0] += 10 #projectile speed
        if projectile[0] < SCREEN_WIDTH:
            frame_index = (projectile[2] + 1) % len(bullet_frames)  # Get the frame index
            screen.blit(bullet_frames[frame_index], (projectile[0], projectile[1]))
            new_projectiles.append([projectile[0], projectile[1], frame_index])
    projectiles = new_projectiles

    pygame.display.update()

    clock.tick(60)

pygame.quit()
