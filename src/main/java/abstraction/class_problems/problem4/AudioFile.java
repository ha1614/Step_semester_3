package abstraction.class_problems.problem4;

public class AudioFile extends MediaFile implements Playable {
    private final String title;

    public AudioFile(String title) {
        super();
        this.title = title;
    }

    @Override
    public String play() {
        return "Playing audio: " + title;
    }

    @Override
    public String play(int fromSecond) {
        return "Playing audio: " + title + " from " + (fromSecond / 60) + ":" + String.format("%02d", fromSecond % 60);
    }

    @Override
    public String pause() {
        return "Paused audio: " + title;
    }

    @Override
    public String getFormatInfo() {
        return "Audio file, ID: " + getFileId();
    }
}
