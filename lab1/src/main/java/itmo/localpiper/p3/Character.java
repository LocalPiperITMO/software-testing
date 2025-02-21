package itmo.localpiper.p3;

public class Character {
    private final String name;
    private String state;
    private Drink drink;

    public Character(String name, String state, Drink drink) {
        this.name = name;
        this.state = state;
        this.drink = drink;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setState(String newState) {
        this.state = newState;
    }

    public void performAction(Action action) {
        if (null != action.getType()) switch (action.getType()) {
            case "Поперхнуться пивом" -> {
                if (!"Потревоженный".equals(this.state)) throw new IllegalStateException(this.name + " не потревожен и не может поперхнуться пивом");
                if (this.drink == null) throw new NullPointerException("Нельзя поперхнуться пивом: пива нет!");
                if (!"Пиво".equals(this.drink.getType())) throw new NotBeerException("Не пиво!");
                System.out.println(this.name + " поперхнулся пивом!");
            }
            case "Вскочить на ноги" -> {
                if (!"Потревоженный".equals(this.state)) throw new IllegalStateException(this.name + " не потревожен и не может вскочить на ноги");
                System.out.print(this.name + " вскочил на ноги!");
            }
            case "Икать" -> {
                if (!"Пьяный".equals(this.state)) throw new IllegalStateException(this.name + " недостаточно выпил, чтобы выполнять данное действие");
                boolean b = false;
                while (!b) {
                    System.out.println(this.name + " икает!");
                    if (Math.random() < 0.5) {
                        ((Character)action.getArgument()).hearSound(new Sound("Икание", this));
                        b = true;
                    }
                }
            }
            default -> {
                System.out.println(this.name + " выполняет действие: " + action.getType());
            }
        }
    }

    public void hearSound(Sound sound) {
        System.out.println(name + " услышал " + sound.getType() + " из: " + ((sound.getSource() == null)? "Ниоткуда" : sound.getSource().toString()));
        if ("Глухой рокот".equals(sound.getType())) {
            setState("Потревоженный");
            performAction(new Action("Поперхнуться пивом", null));
            performAction(new Action("Вскочить на ноги", null));
        } else if ("Икание".equals(sound.getType())) {
            offerDrink((Character)sound.getSource(), new Drink("Виски", 100));
        }
    }

    public void consumeDrink(int quantity) {
        if (this.drink == null) throw new NullPointerException("Нельзя выпить напиток: напитка нет!");
        if (quantity <= 0) throw new IllegalArgumentException("Нельзя пить воздух!");
        if (this.drink.getQuantity() >= quantity) {
            System.out.println(this.name + " выпивает " + quantity + " мл напитка: " + this.drink.getType());
            this.drink.consume(quantity);
            if (Math.random() < 0.2) this.state = "Пьяный";
            if (this.drink.getQuantity() == 0) {
                System.out.println("Напиток выпит: " + this.drink.getType());
                this.drink = null;
            }
        } else throw new NotEnoughDrinkException("Нельзя выпить столько!");
    }

    public void offerDrink(Character other, Drink newDrink) {
        if (other == null) throw new NullPointerException("Некому предложить напиток");
        if (newDrink == null) throw new NullPointerException("Напиток не предложен");
        if (other.getDrink() != null) throw new StillDrinkingException("Нельзя предложить " + newDrink.getType() + ": " + other.getName() + " все еще пьет " + other.getDrink().getType());
        if (newDrink.getQuantity() <= 0) throw new NotEnoughDrinkException("Жадина! Налей больше!");
        other.drink = newDrink;
        System.out.println(name + " угощает " + other.getName() + " напитком: " + newDrink.getType());
    }

    @Override
    public String toString() {
        return this.name;
    }
    
}
