package com.scienjus.smartqq.Shadowsocks;;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Auto-generated: 2017-08-14 17:30:24
 *
 * @author www.jsons.cn
 * @website http://www.jsons.cn/json2java/
 */
public class Data {

    @JsonProperty("raw_data")
    private String rawData;
    @JsonProperty("raw_text")
    private String rawText;
    @JsonProperty("raw_type")
    private String rawType;
    @JsonProperty("raw_format")
    private String rawFormat;
    public void setRawData(String rawData) {
         this.rawData = rawData;
     }
     public String getRawData() {
         return rawData;
     }

    public void setRawText(String rawText) {
         this.rawText = rawText;
     }
     public String getRawText() {
         return rawText;
     }

    public void setRawType(String rawType) {
         this.rawType = rawType;
     }
     public String getRawType() {
         return rawType;
     }

    public void setRawFormat(String rawFormat) {
         this.rawFormat = rawFormat;
     }
     public String getRawFormat() {
         return rawFormat;
     }

}