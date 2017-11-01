package Engine;

public class Message {
    private String content;
    private boolean isFromSystem;

    public Message(String content) {
        this.content = content;
        isFromSystem = true;
    }

    public Message(String content, boolean isFromSystem) {
        this.content = content;
        this.isFromSystem = isFromSystem;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFromSystem() {
        return isFromSystem;
    }

    public void setFromSystem(boolean fromSystem) {
        isFromSystem = fromSystem;
    }
}
