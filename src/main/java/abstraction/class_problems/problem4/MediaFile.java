package abstraction.class_problems.problem4;

public abstract class MediaFile {
    private static int fileCounter = 1000;

    private final String fileId;

    protected MediaFile() {
        fileCounter++;
        this.fileId = "MF-" + fileCounter;
    }

    public abstract String getFormatInfo();

    String getFileId() {
        return fileId;
    }
}
