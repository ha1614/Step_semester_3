package abstraction.class_problems.problem4;

public class MediaLauncher {

    static void launchAll(Playable[] items) {
        for (Playable item : items) {
            System.out.println(item.play());
        }
    }

    public static void main(String[] args) {
        AudioFile a = new AudioFile("Morning Jazz");
        System.out.println(a.play());
        System.out.println(a.play(30));
        System.out.println(a.getFormatInfo());

        Podcast p = new Podcast("Tech Talk", 12);
        System.out.println(p.play());

        Playable ref = a; // upcasting: AudioFile reference stored as the Playable interface type
        System.out.println(ref.play());

        launchAll(new Playable[]{ref, p});
    }
}
