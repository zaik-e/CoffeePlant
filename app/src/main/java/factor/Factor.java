package factor;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;


public class Factor implements Comparable<Factor> {

  public final @NotNull String value;

  public Factor(@NotNull String str) {
    value = str;
  }

  public Factor() {
    value = "";
  }

  public Factor(Character c) {
    value = c.toString();
  }

  public int length() {
    return value.length();
  }

  public boolean isEmpty() {
    return (value.equals(""));
  }

  public static @NotNull Factor getEmpty() {
    return new Factor("");
  }

  public boolean startsWith(@NotNull Factor a) {
    return (value.startsWith(a.value));
  }

  public boolean endsWith(@NotNull Factor a) {
    return (value.endsWith(a.value));
  }

  public boolean contains(@NotNull Factor a) {
    return (value.contains(a.value));
  }

  public @NotNull Factor concat(@NotNull Factor that) {
    return new Factor(value.concat(that.value));
  }

  public Character charAt(int c) {
    return (value.charAt(c));
  }

  public int indexOf(@NotNull Factor that) {
      return this.value.indexOf(that.value);
  }

  public int lastIndexOf(@NotNull Factor that) {
      return this.value.lastIndexOf(that.value);
  }

  public @NotNull Factor substring(int left, int right) {
    if (left<=right && left >=0 && right <= this.length())
      return new Factor(value.substring(left, right));
    else
      throw new IllegalArgumentException();
  }

  public @NotNull Factor substring(int left) {
    if (left >=0 && left <= this.length())
      return new Factor(value.substring(left));
    else
      throw new IllegalArgumentException();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof final @NotNull Factor that)) {
      return false;
    }
    return (this.value.equals(that.value));
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public @NotNull String toString() {
    return value;
  }

  @Override
  public int compareTo(@NotNull Factor o) {
    return Integer.compare(this.length() - o.length(), 0);
  }
}
