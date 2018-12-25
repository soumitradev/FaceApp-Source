package org.catrobat.catroid.scratchconverter.protocol.message;

import android.util.SparseArray;

public abstract class Message {

    public enum CategoryType {
        BASE(0),
        JOB(1);
        
        private static SparseArray<CategoryType> categoryTypes;
        private int categoryID;

        static {
            categoryTypes = new SparseArray();
            CategoryType[] values = values();
            int length = values.length;
            int i;
            while (i < length) {
                CategoryType legEnum = values[i];
                categoryTypes.put(legEnum.categoryID, legEnum);
                i++;
            }
        }

        private CategoryType(int categoryID) {
            this.categoryID = categoryID;
        }

        public static CategoryType valueOf(int categoryID) {
            return (CategoryType) categoryTypes.get(categoryID);
        }

        public int getCategoryID() {
            return this.categoryID;
        }
    }
}
