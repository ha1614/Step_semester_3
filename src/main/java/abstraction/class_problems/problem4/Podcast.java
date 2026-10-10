package abstraction.class_problems.problem4;

/*
 * Podcast is only a Playable (CAN-DO), not a MediaFile (IS-A): it is streamed live and has no
 * stored file, so it would gain nothing from a fileId or getFormatInfo() it has no use for.
 * AudioFile genuinely IS a media file and shares MediaFile's real state, so it extends it.
 */
public class Podcast implements Playable {
    private final String showName;
    private final int episodeNumber;

    public Podcast(String showName, int episodeNumber) {
        if (episodeNumber <= 0) {
            throw new IllegalArgumentException("episodeNumber must be positive");
        }
        this.showName = showName;
        this.episodeNumber = episodeNumber;
    }

    @Override
    public String play() {
        return "Streaming episode " + episodeNumber + " of " + showName;
    }

    @Override
    public String play(int fromSecond) {
        return play() + " from second " + fromSecond;
    }

    @Override
    public String pause() {
        return "Paused episode " + episodeNumber + " of " + showName;
    }
}
