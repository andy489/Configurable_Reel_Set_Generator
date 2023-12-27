package wrapper;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ConvertWrapper {
    @JsonAlias({"do", "convert"})
    private boolean confirm;

    @JsonAlias({"com", "formatType", "format"})
    private String toCom;

    @JsonAlias({"src"})
    private String src;

    @JsonAlias({"dest"})
    private String dest;

    public boolean isConfirm() {
        return confirm;
    }

    public ConvertWrapper setConfirm(boolean confirm) {
        this.confirm = confirm;
        return this;
    }

    public String getToCom() {
        return toCom;
    }

    public ConvertWrapper setToCom(String toCom) {
        this.toCom = toCom;
        return this;
    }

    public String getSrc() {
        return src;
    }

    public ConvertWrapper setSrc(String src) {
        this.src = src;
        return this;
    }

    public String getDest() {
        return dest;
    }

    public ConvertWrapper setDest(String dest) {
        this.dest = dest;
        return this;
    }

    @Override
    public String toString() {
        return "ConvertWrapper{" +
                "confirm=" + confirm +
                ", toCom='" + toCom + '\'' +
                ", src='" + src + '\'' +
                ", dest='" + dest + '\'' +
                '}';
    }
}
