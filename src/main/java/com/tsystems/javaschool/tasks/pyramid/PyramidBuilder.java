package com.tsystems.javaschool.tasks.pyramid;

import java.util.Iterator;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minimum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // determine the possibility of the pyramid
        // as well as its width and height
        int height = 0;
        int width = 1;
        int size = inputNumbers.size();
        while (size > 0) {
            if (height > 0) {
                width += 2;
            }
            size -= ++height;
        }
        if (size < 0) {
            // pyramid is not possible
            throw new CannotBuildPyramidException();
        }
        // sort the input list ascending, fail if null elements found
        inputNumbers.sort((n1, n2) -> {
            if (n1 == null || n2 == null) {
                throw new CannotBuildPyramidException();
            }
            return n1.compareTo(n2);
        });
        // build the pyramid
        int[][] pyramid = new int[height][width];
        Iterator<Integer> it = inputNumbers.iterator();
        for (int i = 0; i < height; i++) {
            int n = i + 1;  // number of elements to read from inputNumbers on this row
            boolean nextZero = false;
            for (int j = 0; j < width; j++) {
                if (j < height - n || n == 0 || nextZero) {
                    pyramid[i][j] = 0;
                    nextZero = false;
                }
                else if (n-- > 0) {
                    pyramid[i][j] = it.next();
                    nextZero = true;
                }
            }
        }
        return pyramid;
    }
}
