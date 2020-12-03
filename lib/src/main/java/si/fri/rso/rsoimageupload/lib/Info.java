package si.fri.rso.rsoimageupload.lib;

import java.util.List;

public class Info {
    private List<String> clani;
    String opis_projekta;
    private List<String> mikrostoritve;
    private List<String> github;
    private List<String> travis;
    private List<String> dockerhub;

    public List<String> getClani() {
        return clani;
    }

    public void setClani(List<String> clani) {
        this.clani = clani;
    }

    public String getOpis_projekta() {
        return opis_projekta;
    }

    public void setOpis_projekta(String opis_projekta) {
        this.opis_projekta = opis_projekta;
    }

    public List<String> getMikrostoritve() {
        return mikrostoritve;
    }

    public void setMikrostoritve(List<String> mikrostoritve) {
        this.mikrostoritve = mikrostoritve;
    }

    public List<String> getGithub() {
        return github;
    }

    public void setGithub(List<String> github) {
        this.github = github;
    }

    public List<String> getTravis() {
        return travis;
    }

    public void setTravis(List<String> travis) {
        this.travis = travis;
    }

    public List<String> getDockerhub() {
        return dockerhub;
    }

    public void setDockerhub(List<String> dockerhub) {
        this.dockerhub = dockerhub;
    }
}
