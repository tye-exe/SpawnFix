package me.tye.spawnfix.utils;

import org.checkerframework.checker.units.qual.K;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static me.tye.spawnfix.utils.Util.log;

public enum Config {

  default_worldName(String.class),
  default_x(Double.class),
  default_y(Double.class),
  default_z(Double.class),
  default_yaw(Float.class),
  default_pitch(Float.class),

  teleport_times(Integer.class),
  teleport_retryInterval(Integer.class),

  login(Occurrence.class),
  onSpawn(Occurrence.class),
  lang(String.class);



/**
 * @param type Should be set to the class of the object this enum will be parsed as. This checks that the config values entered are valid for the used key.
 */
Config(Class type) {
  this.type = type;
}

/**
 Stores the class this object should be parsed as.
 */
private final Class type;

/**
 * @return The class of the object this enum should be parsed as.
 */
private Class getType() {
  return type;
}


/**
 Stores the configs for this plugin.
 */
private static final HashMap<Config, Object> configs = new HashMap<>();


/**
 * @return Gets the config response for the selected enum.
 */
public @NotNull Object getConfig() {
  Object response = configs.get(this);

  assert response != null;

  return response;
}

/**
 * @return Gets the config response for the selected enum wrapped with String.valueOf().
 */
public @NotNull String getStringConfig() {
  return String.valueOf(getConfig());
}

/**
 * @return Gets the config response for the selected enum wrapped with Integer.parseInt().
 */
public @NotNull Integer getIntegerConfig() {
  return Integer.parseInt(getStringConfig());
}


/**
 * @return Gets the config response for the selected enum wrapped with Double.parseDouble().
 */
public @NotNull Double getDoubleConfig() {
  return Double.parseDouble(getStringConfig());
}

/**
 Enum for how often spawnFix should act for a certain feature.
 */
public enum Occurrence {
  NEVER,
  FIRST,
  EVERY;
}

/**
 * @return Gets the config response for the selected enum wrapped with Occurrence.valueOf().
 */
public @NotNull Occurrence getOccurrenceConfig() {
  return Occurrence.valueOf(getStringConfig().toUpperCase());
}

/**
 Loads the default configs.
 */
public static void init() {
  //Loads the default values into the config.
  HashMap<String,Object> internalConfig = Util.parseInternalYaml("config.yml");

  internalConfig.forEach((String key, Object value) -> {
    String formattedKey = key.replace('.', '_');

    try {
      Config config = Config.valueOf(formattedKey);

      if (!validate(config, value)) {
        //Dev warning
        throw new RuntimeException("\""+config+"\" cannot be parsed as given object. - Dev warning");
      }

      configs.put(config, value);

    } catch (IllegalArgumentException e) {
      //Dev warning
      throw new RuntimeException("\""+formattedKey + "\" isn't in default config file.  - Dev warning");
    }
  });

  //Checks if any default values are missing.
  for (Config config : Config.values()) {
    if (configs.containsKey(config)) continue;

    //Dev warning.
    throw new RuntimeException("\""+config+"\" isn't in default config file.  - Dev warning");
  }
}

/**
 Puts the keys response specified by the user into the configs map.
 */
public static void load() {
  //Loads in the user-set configs.
  File externalConfigFile = new File(Util.dataFolder.toPath()+File.separator+"config.yml");
  HashMap<String,Object> externalConfigs = Util.parseAndRepairExternalYaml(externalConfigFile, "config.yml");

  HashMap<Config, Object> userConfigs = new HashMap<>();

  //Gets the default keys that the user has entered.
  for (Map.Entry<String, Object> entry : externalConfigs.entrySet()) {
    String key = entry.getKey();
    Object value = entry.getValue();

    String formattedKey = key.replace('.', '_');
    Config config = Config.valueOf(formattedKey);

    if (!validate(config, value)) {
      log.warning(Lang.excepts_invalidValue.getResponse(Key.key.replaceWith(key), Key.filePath.replaceWith(externalConfigFile.getAbsolutePath())));
      continue;
    }

    //logs an exception if the key doesn't exist.
    try {
      userConfigs.put(config, value);

    } catch (IllegalArgumentException e) {
      log.warning(Lang.excepts_invalidKey.getResponse(Key.key.replaceWith(key)));
    }
  }


  //Warns the user about any config keys they are missing.
  for (Config config : configs.keySet()) {
    if (userConfigs.containsKey(config)) continue;

    log.warning(Lang.excepts_missingKey.getResponse(Key.key.replaceWith(config.toString()), Key.filePath.replaceWith(externalConfigFile.getAbsolutePath())));
  }

  configs.putAll(userConfigs);
}

/**
 Checks if config can be parsed as its intended object.
 * @param config The config to check.
 * @param value The value of the config.
 * @return True if the config can be parsed as its intended object. False if it can't.
 */
private static boolean validate(Config config, Object value) {
  Class configType = config.getType();

  //Strings can always be parsed.
  if (configType.equals(String.class)) return true;

  String stringValue = value.toString();

  if (configType.equals(Double.class)) {
    try {
      Double.parseDouble(stringValue);
      return true;
    } catch (Exception ignore) {
      return false;
    }
  }

  if (configType.equals(Occurrence.class)) {
    try {
      Occurrence.valueOf(stringValue.toUpperCase());
      return true;
    } catch (Exception ignore) {
      return false;
    }
  }

  if (configType.equals(Integer.class)) {
    try {
      Integer.parseInt(stringValue);
      return true;
    } catch (Exception ignore) {
      return false;
    }
  }

  if (configType.equals(Float.class)) {
    try {
      Float.parseFloat(stringValue);
      return true;
    } catch (Exception ignore) {
      return false;
    }
  }

  throw new RuntimeException("Validation for class \""+configType+"\" does not exist! - Dev warning.");
}

}
