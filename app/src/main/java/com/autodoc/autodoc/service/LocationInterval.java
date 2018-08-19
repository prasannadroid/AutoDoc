package lk.hire1.driver.services;

public enum LocationInterval {
    HALF_SECOND(500, 250),
    ONE_SECOND(1000, 500),
    TWO_SECONDS(2000, 1000),
    FOUR_SECONDS(4000, 2000),
    FIVE_SECONDS(5000, 2500),
    EIGHT_SECONDS(8000, 4000),
    TEN_SECONDS(10000, 8000),
    TWENTY_SECONDS(20000, 10000),
    FORTY_SECONDS(40000, 20000);

    private final long interval;
    private final long fastInterval;

    LocationInterval(long interval, long fastInterval) {
        this.interval = interval;
        this.fastInterval = fastInterval;
    }

    public long getInterval() {
        return interval;
    }

    public long getFastInterval() {
        return fastInterval;
    }
}
