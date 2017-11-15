package com.example.rick.agileitticket;
import com.example.rick.agileitticket.android.Global;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rick on 14.11.17.
 */

public class ApiClientCall {
    private String name;

        public DocName(String name) {
            this.name = name;
        }

        /**
         * @return The name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name The name
         */
        public void setName(String name) {
            this.name = name;
        }

}
