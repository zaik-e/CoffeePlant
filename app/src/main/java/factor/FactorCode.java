package factor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;


public class FactorCode {

  @NotNull Set<Factor> factors;

  public FactorCode(@NotNull Set<Factor> ideals) {
    this.factors = ideals;
  }

  public FactorCode(@NotNull String str) {
    factors = new HashSet<>();
    if (!(str.isEmpty()))
	  factors.add(new Factor(str));
  }

  public FactorCode(@NotNull Factor f) {
    Set<Factor> iset = new HashSet<>();
    if (!(f.isEmpty()))
	    iset.add(f);
    this.factors = iset;
  }

  public boolean isEmpty() {
    return factors.isEmpty();
  }

  public static @NotNull FactorCode getEmpty() {
    return new FactorCode(new HashSet<>());
  }

  public int size() {
    return factors.size();
  }

  public int commonLength() {
    int res = 0;
    for (Factor f : factors) {
      res += f.length();
    }
    return res;
  }

  public @NotNull FactorCode copy() {
    return new FactorCode(new HashSet<>(this.factors));
  }

  public @NotNull Set<Factor> getFactors() {
    return this.factors;
  }

  public static @NotNull Set<Factor> addFactorToSet(@NotNull Set<Factor> factors, @NotNull Factor f) {
    throw new UnsupportedOperationException();
  }

  public @NotNull FactorCode addFactor(@NotNull Factor f) {
    return new FactorCode(addFactorToSet(this.factors, f));
  }

  public @NotNull FactorCode addAllFactors(@NotNull FactorCode that) {
    throw new UnsupportedOperationException();
  }

  public static @NotNull FactorCode normalizeCode(@NotNull FactorCode that) {
    throw new UnsupportedOperationException();
  }

  public @NotNull FactorCode reverse() {
    throw new UnsupportedOperationException();
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj instanceof FactorCode that) {
      return (this.factors.equals(that.factors));
    }
    return false;                                                      
  }

  @Override
  public int hashCode() {
    return factors.hashCode();
  }

}
