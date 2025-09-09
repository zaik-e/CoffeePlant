package factor;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import sufftree.GeneralizedSuffixTree;


public class FactorCode {
  private @NotNull GeneralizedSuffixTree tree = new GeneralizedSuffixTree();

  public FactorCode(@NotNull Set<Factor> factors) {
    this.tree = new GeneralizedSuffixTree(new ArrayList<>(factors));
  }

  public FactorCode(@NotNull ArrayList<Factor> factors) {
    this.tree = new GeneralizedSuffixTree(factors);
  }

  public FactorCode(@NotNull String str) {
    ArrayList<Factor> factors = new ArrayList<>();
    factors.add(new Factor(str));
    this.tree = new GeneralizedSuffixTree(factors);
  }

  public FactorCode(@NotNull Factor f) {
    ArrayList<Factor> factors = new ArrayList<>();
    factors.add(f);
    this.tree = new GeneralizedSuffixTree(factors);
  }

  public FactorCode(@NotNull GeneralizedSuffixTree tree) {
    this.tree = tree;
  }

  private void setTree(@NotNull GeneralizedSuffixTree st) {
    this.tree = st;
  }

  public boolean isEmpty() {
    return tree.isEmpty();
  }

  public static @NotNull FactorCode getEmpty() {
    return new FactorCode(new ArrayList<>());
  }

  public int size() {
    return tree.size();
  }

  public int commonLength() {
    int res = 0;
    for (Factor f : tree.getFactors()) {
      res += f.length();
    }
    return res;
  }

  public @NotNull FactorCode copy() {
    return new FactorCode(this.tree.clearCopy());
  }

  public @NotNull List<Factor> getFactors() {
    return this.tree.getFactors();
  }

  private void addFactor(@NotNull Factor f) {
    tree.addFactor(f);
  }

  public void addAllFactors(@NotNull FactorCode that) {
    for (Factor f : that.getFactors()) {
      tree.addFactor(f);
    }
  }

  public static @NotNull FactorCode normalizeCode(@NotNull FactorCode that) {
    FactorCode resultCode = FactorCode.getEmpty();
    for (Factor f : that.getFactors()) {
      resultCode.addFactor(f);
    }
    return resultCode;
  }

  public @NotNull FactorCode joinCode(@NotNull FactorCode that) {
    ArrayList<Factor> mergeFactors = new ArrayList<>(this.getFactors());
    int divider = mergeFactors.size() - 1;
    mergeFactors.addAll(that.getFactors());
    GeneralizedSuffixTree commonTree = new GeneralizedSuffixTree(mergeFactors);
    ArrayList<Factor> lcss = commonTree.LCSSoddeven(divider);
    return new FactorCode(lcss);
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
      Set<Factor> thisSet = new HashSet<>(this.getFactors());
      Set<Factor> thatSet = new HashSet<>(that.getFactors());
      return thisSet.equals(thatSet);
    }
    return false;                                                      
  }

  @Override
  public int hashCode() {
    return getFactors().hashCode();
  }

}
