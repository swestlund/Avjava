package org.example;
import java.util.ArrayList;


// public
public class EffectService {



    //private så att ingen kan radera/byta namn på listan
    private List<Effect> effects = new ArrayList<Effect>();



    //public (en del av APIet??) så att användare kan hämta objektens egenskaper och lägga till
    public void display(Effect effect) {
        effect.display();
        effects.add(effect);
    }


    //private så att användare inte kan ändra på denna funktion
    private void tickEffects() {
        Iterator<Effect> iter = effects.iterator();
        while (iter.hasNext()) {
            Effect effect = iter.next();
            if (!effect.shouldTick()) {
                iter.remove();
                continue;
            }
            effect.tick();
        }
    }

    // public (en del av API??) så att användare kan ta del av funktionerna
    public interface Effect {
        void display();
        void tick();
        boolean shouldTick();
    }
}

}