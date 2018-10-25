package eu.antonkrug;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import eu.antonkrug.model.Filter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.File;

/**
 * @author Anton Krug on 24/10/18
 * @version v0.1
 */
public class Converter {

  public static Font applyEffect(Font origin, String effectName) {
    Font ret = new FontDefault();
    ret.setPath(origin.getPath());

    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    try {
      Filter effect = mapper.readValue(new File("./src/main/resources/filters/"+effectName+".yml"), Filter.class);
      System.out.println(ReflectionToStringBuilder.toString(effect, ToStringStyle.MULTI_LINE_STYLE));
      //      System.out.println(effect.getEffect().getPaint().getBytecode());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


    return ret;
  }


  public static void main(String[] args) throws Exception {
    System.out.println("Converter started...");



    Font font = new FontMcm("/home/fredy/Desktop/a");
    font.load();

    Font fontWithEffect = font.applyFilter("resource:/outline-2x");

    Font fontPng = new FontPng(fontWithEffect);
    fontPng.save();
  }


}
