import pygame

pygame.font.init()
font = pygame.font.Font(None, 36)  # You can adjust the font size as needed

def draw_hud(screen, player1_hp, player2_hp):
    player1_hp_text = font.render(f"Player One HP: {player1_hp}", True, (255, 255, 255))
    player2_hp_text = font.render(f"Player Two HP: {player2_hp}", True, (255, 255, 255))
    
    screen.blit(player1_hp_text, (10, 10))  # Adjust the position as needed
    screen.blit(player2_hp_text, (10, 50))  # Adjust the position as needed
