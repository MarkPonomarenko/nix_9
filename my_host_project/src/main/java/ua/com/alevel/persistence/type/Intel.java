package ua.com.alevel.persistence.type;

public enum  Intel {
    ae("Xeon E-2386G", 6, 3.50),
    be("Xeon E-2388G", 8, 3.20),
    ce("Xeon E-2374G", 4, 3.70),
    aw("Xeon W-11555MLE", 6, 1.90),
    bw("Xeon W-11155MLE", 4, 1.80),
    cw("Xeon W-11865MLE", 8, 1.50),
    ad("Xeon D-1649N", 8, 2.30),
    bd("Xeon D-1623N", 4, 2.40),
    cd("Xeon D-1633N", 6, 2.50)
    ;


    private String name;
    private Integer cores;
    private Double frequency;

    public String getName() {
        return name;
    }

    public Integer getCores() {
        return cores;
    }

    public Double getFrequency() {
        return frequency;
    }

    Intel(String name, Integer cores, Double frequency) {
        this.name = name;
        this.cores = cores;
        this.frequency = frequency;
    }

}
