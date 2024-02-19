import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int medicHeal = 50;
    public static int[] heroesHealth = {290, 270, 250, 400};
    public static int[] heroesDamage = {20, 15, 10, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Piercing", "Medic"};
    public static int roundNumber = 0;

    public static void main(String[] args) {
        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }

        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }

        return allHeroesDead;
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length - 1);
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttacks();
        heroesAttack();
        showStatistics();
    }

    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                heroesHealth[i] = Math.max(0, heroesHealth[i] - bossDamage);
                if (heroesHealth[i] < 0) {
                    heroesHealth[i] = 0;
                }
            }
        }
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i].equals(bossDefence)) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2;
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }

                // Лечение Medic после атаки героев, если он жив и жизни героев меньше 100
                if (heroesAttackType[i].equals("Medic") && heroesHealth[i] > 0 && isHeroesHealthBelow100()) {
                    medicHeal();
                } else {
                    bossHealth = Math.max(0, bossHealth - damage);
                    if (bossHealth < 0) {
                        bossHealth = 0;
                    }
                }
            }
        }
    }

    public static boolean isHeroesHealthBelow100() {
        for (int health : heroesHealth) {
            if (health < 100) {
                return true;
            }
        }
        return false;
    }

    public static void medicHeal() {
        Random random = new Random();
        int targetIndex;
        do {
            targetIndex = random.nextInt(heroesHealth.length - 1);
        } while (heroesHealth[targetIndex] >= 100 || heroesHealth[targetIndex] <= 0);

        int healAmount = Math.min(medicHeal, 100 - heroesHealth[targetIndex]);
        heroesHealth[targetIndex] += healAmount;
        System.out.println("Medic healed " + heroesAttackType[targetIndex] + " for " + healAmount + " health points.");
    }

    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber + " -------------");
        System.out.print("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: ");
        if (bossDefence != null) {
            System.out.print(bossDefence);
        } else {
            System.out.print("No defence");
        }
        System.out.println();

        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
        System.out.println();
    }
}
