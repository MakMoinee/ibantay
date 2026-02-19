package com.thesis.dogharness.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalSettings {
    private String settingName;
    private int imageResource;

    public LocalSettings(LocalSettingsBuilder builder) {
        this.settingName = builder.settingName;
        this.imageResource = builder.imageResource;
    }

    public static class LocalSettingsBuilder{
        private String settingName;
        private int imageResource;

        public LocalSettingsBuilder setSettingName(String settingName) {
            this.settingName = settingName;
            return this;
        }

        public LocalSettingsBuilder setImageResource(int imageResource) {
            this.imageResource = imageResource;
            return this;
        }

        public LocalSettings build(){
            return new LocalSettings(this);
        }
    }
}
