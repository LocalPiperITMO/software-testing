package itmo.localpiper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import itmo.localpiper.p3.Action;
import itmo.localpiper.p3.Character;
import itmo.localpiper.p3.Drink;
import itmo.localpiper.p3.NotEnoughDrinkException;
import itmo.localpiper.p3.Sound;
import itmo.localpiper.p3.StillDrinkingException;

class DomainTest {
    private Character p1;
    private Character p2;
    private final int maxBeer = Integer.MAX_VALUE;
    private final int minBeer = Integer.MIN_VALUE;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        p1 = new Character("Петя", "Спокойный", null);
        p2 = new Character("Вася", "Спокойный", null);
    }

    @Test
    void testDrinkTooMuch() {
        Character poorGuy = new Character("Шиз", "Пьяный", new Drink("Виски", 9999));
        NullPointerException e = assertThrows(NullPointerException.class,()->poorGuy.hearSound(new Sound("Икание", null)));
        assertEquals("Некому предложить напиток", e.getMessage());

    }

    @Test
    void testBeerPCPattern() {
        for (int i = 0; i < 100000; ++i) {
            testBuyDrink();
            testDrinkBeer();
        }
    }
    

    void testBuyDrink() {
        int volume = (int)((Math.random() * ((long) maxBeer - minBeer)) + minBeer);

        Drink beer = new Drink("Пиво", volume);
    
        if (p2.getDrink() != null) {
            StillDrinkingException e = assertThrows(StillDrinkingException.class, () -> p1.offerDrink(p2, beer));
            assertEquals("Нельзя предложить Пиво: Вася все еще пьет Пиво", e.getMessage());
        } else if (volume <= 0) {
            NotEnoughDrinkException e = assertThrows(NotEnoughDrinkException.class, () -> p1.offerDrink(p2, beer));
            assertEquals("Жадина! Налей больше!", e.getMessage());
        } else {
            assertDoesNotThrow(() -> p1.offerDrink(p2, beer));
            assertNotNull(p2.getDrink());
            assertEquals("Пиво", p2.getDrink().getType());
        }
    }
    
    void testDrinkBeer() {
        int quantity = (int)((Math.random() * ((long) maxBeer - minBeer)) + minBeer);
    
        if (p2.getDrink() == null) {
            NullPointerException e = assertThrows(NullPointerException.class, () -> p2.consumeDrink(quantity));
            assertEquals("Нельзя выпить напиток: напитка нет!", e.getMessage());
        } else {
            if (quantity <= 0) {
                IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> p2.consumeDrink(quantity));
                assertEquals("Нельзя пить воздух!", e.getMessage());
            } else if (p2.getDrink().getQuantity() < quantity) {
                NotEnoughDrinkException e = assertThrows(NotEnoughDrinkException.class, () -> p2.consumeDrink(quantity));
                assertEquals("Нельзя выпить столько!", e.getMessage());
            } else {
                assertDoesNotThrow(() -> p2.consumeDrink(quantity));
            }
        }
    } 

    @Test
    @DisplayName("Assure the story goes on")
    void testStory() {
        Character arthur = new Character("Артур", "Спокойный", new Drink("Пиво", 100));
        Character ford = new Character("Форд", "Спокойный", null);
        Character guyNearFord = new Character("Человек рядом с Фордом", "Пьяный", null);

        arthur.consumeDrink(50);
        ford.performAction(new Action("Спать", null));
        arthur.hearSound(new Sound("Разговоры людей", "Люди"));
        arthur.hearSound(new Sound("Музыка", "Музыкальный автомат"));
        guyNearFord.performAction(new Action("Икать", arthur));
        arthur.hearSound(new Sound("Глухой рокот", "Снаружи"));
        assertEquals(true, true);
    }
}
