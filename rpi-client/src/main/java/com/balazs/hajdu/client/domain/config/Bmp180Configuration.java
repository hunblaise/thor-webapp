package com.balazs.hajdu.client.domain.config;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.pi4j.io.i2c.I2CDevice;

/**
 * BMP180 sensor configuration.
 *
 * @author Balazs Hajdu
 */
public class Bmp180Configuration {

    private final I2CDevice device;
    // EEPROM registers - these represents calibration data
    private final short ac1;
    private final short ac2;
    private final short ac3;
    private final int ac4;
    private final int ac5;
    private final int ac6;

    private final short b1;
    private final short b2;

    private final short mb;
    private final short mc;
    private final short md;

    private Bmp180Configuration(Builder builder) {
        this.device = builder.device;
        this.ac1 = builder.ac1;
        this.ac2 = builder.ac2;
        this.ac3 = builder.ac3;
        this.ac4 = builder.ac4;
        this.ac5 = builder.ac5;
        this.ac6 = builder.ac6;
        this.b1 = builder.b1;
        this.b2 = builder.b2;
        this.mb = builder.mb;
        this.mc = builder.mc;
        this.md = builder.md;
    }

    public I2CDevice getDevice() {
        return device;
    }

    public short getAc1() {
        return ac1;
    }

    public short getAc2() {
        return ac2;
    }

    public short getAc3() {
        return ac3;
    }

    public int getAc4() {
        return ac4;
    }

    public int getAc5() {
        return ac5;
    }

    public int getAc6() {
        return ac6;
    }

    public short getB1() {
        return b1;
    }

    public short getB2() {
        return b2;
    }

    public short getMb() {
        return mb;
    }

    public short getMc() {
        return mc;
    }

    public short getMd() {
        return md;
    }

    // generated code begins here
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bmp180Configuration that = (Bmp180Configuration) o;
        return ac1 == that.ac1 &&
                ac2 == that.ac2 &&
                ac3 == that.ac3 &&
                ac4 == that.ac4 &&
                ac5 == that.ac5 &&
                ac6 == that.ac6 &&
                b1 == that.b1 &&
                b2 == that.b2 &&
                mb == that.mb &&
                mc == that.mc &&
                md == that.md &&
                Objects.equal(device, that.device);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(device, ac1, ac2, ac3, ac4, ac5, ac6, b1, b2, mb, mc, md);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("device", device)
                .add("ac1", ac1)
                .add("ac2", ac2)
                .add("ac3", ac3)
                .add("ac4", ac4)
                .add("ac5", ac5)
                .add("ac6", ac6)
                .add("b1", b1)
                .add("b2", b2)
                .add("mb", mb)
                .add("mc", mc)
                .add("md", md)
                .toString();
    }

    public static class Builder {

        private I2CDevice device;
        private short ac1;
        private short ac2;
        private short ac3;
        private int ac4;
        private int ac5;
        private int ac6;

        private short b1;
        private short b2;

        private short mb;
        private short mc;
        private short md;

        public Builder withDevice(I2CDevice device) {
            this.device = device;
            return this;
        }

        public Builder withAc1(short ac1) {
            this.ac1 = ac1;
            return this;
        }

        public Builder withAc2(short ac2) {
            this.ac2 = ac2;
            return this;
        }

        public Builder withAc3(short ac3) {
            this.ac3 = ac3;
            return this;
        }

        public Builder withAc4(int ac4) {
            this.ac4 = ac4;
            return this;
        }

        public Builder withAc5(int ac5) {
            this.ac5 = ac5;
            return this;
        }

        public Builder withAc6(int ac6) {
            this.ac6 = ac6;
            return this;
        }

        public Builder withB1(short b1) {
            this.b1 = b1;
            return this;
        }

        public Builder withB2(short b2) {
            this.b2 = b2;
            return this;
        }

        public Builder withMb(short mb) {
            this.mb = mb;
            return this;
        }

        public Builder withMc(short mc) {
            this.mc = mc;
            return this;
        }

        public Builder withMd(short md) {
            this.md = md;
            return this;
        }

        public Bmp180Configuration build() {
            return new Bmp180Configuration(this);
        }

    }
    // generated code ends here
}
