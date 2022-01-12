package io.github.mmm.entity.bean.sql;

/**
 * Class to map from Java {@link Class} to their {@link Class#getName()} or {@link Class#getSimpleName() simple name}
 * and vice-versa in a secure way (without {@link ClassLoader} injection).
 *
 */
public class JavaClassNameMapper {

  private static final JavaClassNameMapper INSTANCE = new JavaClassNameMapper();

  public String getName(Class<?> type) {

    String name = type.getName();
    if (name.startsWith("java.lang.")) {
      name = name.substring(10);
    } else if (name.startsWith("io.github.mmm.")) {
      int lastDot = name.lastIndexOf('.');
      name = name.substring(lastDot + 1);
    }
    return name;
  }

  /**
   * @return the {@link JavaClassNameMapper}.
   */
  public static JavaClassNameMapper get() {

    return INSTANCE;
  }

}
