package unused;

/**
 *
 * @author desha
 */
public class TRA {

    static final int totId = 0;
    static final int fifId = 1;
    static final int pairId = 2;
    static final int serieId = 3;
    static final int nobId = 4;
    static final int flushId = 5;

    int[] vals;

    int compteurNH;

    public TRA(int i1, int i2, int i3, int i4, int i5, int compt) {
        vals[1] = i1;
        vals[2] = i2;
        vals[3] = i3;
        vals[4] = i4;
        vals[5] = i5;
        vals[0] = i1 + i2 + i3 + i4 + i5;
        compteurNH = compt;

    }

}
