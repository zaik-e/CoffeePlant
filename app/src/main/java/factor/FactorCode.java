package factor;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sufftree.GeneralizedSuffixTree;


public class FactorCode {

//  @NotNull Set<Factor> factors;
  @NotNull ArrayList<Factor> factors;
  @Nullable GeneralizedSuffixTree tree = null;

  public FactorCode(@NotNull ArrayList<Factor> ideals) {
    this.factors = ideals;
  }

  public FactorCode(@NotNull String str) {
    factors = new ArrayList<>();
    if (!(str.isEmpty()))
	  factors.add(new Factor(str));
  }

  public FactorCode(@NotNull Factor f) {
    ArrayList<Factor> iset = new ArrayList<>();
    if (!(f.isEmpty()))
	    iset.add(f);
    this.factors = iset;
  }

  public boolean isEmpty() {
    return factors.isEmpty();
  }

  public static @NotNull FactorCode getEmpty() {
    return new FactorCode(new ArrayList<>());
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
    return new FactorCode(new ArrayList<>(this.factors));
  }

  public @NotNull List<Factor> getFactors() {
    return this.factors;
  }

  /*check with empty*/
  public void addFactor(@NotNull Factor f) {
    if (tree == null) {
      GeneralizedSuffixTree currTree = new GeneralizedSuffixTree(factors);
    }
    int indexAdded = tree.addFactor(f);
    if (indexAdded != -1)
      factors.add(indexAdded, f);
  }

  public void addAllFactors(@NotNull FactorCode that) {
    for (Factor thatFactor : that.factors) {
      this.addFactor(thatFactor);
    }
  }

  public static @NotNull FactorCode normalizeCode(@NotNull FactorCode that) {
    List<Factor> thatFactors = that.factors;
    Collections.sort(thatFactors);
    FactorCode resultCode = FactorCode.getEmpty();
    for (int index = thatFactors.size() - 1; index >= 0; index--) {
      resultCode.addFactor(thatFactors.get(index));
    }
    return resultCode;
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
