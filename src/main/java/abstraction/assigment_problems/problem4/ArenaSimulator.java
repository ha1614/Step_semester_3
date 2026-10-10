package abstraction.assigment_problems.problem4;

public class ArenaSimulator {

    static void resolveDefense(Defendable[] combatants) {
        for (Defendable d : combatants) {
            System.out.println(d.defend());
        }
    }

    public static void main(String[] args) {
        Warrior w = new Warrior("Kael");
        System.out.println(w.attack());
        System.out.println(w.attack("Iron Sword"));
        System.out.println(w.defend());
        System.out.println(w.getSpecialMove());

        Trap t = new Trap("Spike Pit");
        System.out.println(t.defend());

        resolveDefense(new Defendable[]{w, t});
    }
}
