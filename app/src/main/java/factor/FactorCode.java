package factor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import sufftree.GeneralizedSuffixTree;
import org.jetbrains.annotations.NotNull;

/**
 * Class of equation systems of linear equations
 * <p> Possible equations forms:
 * <ul>
 * <li> <i>Z = Xi w Yi</i>
 * </ul>
 * The <i>Z</i>-projection of their solution set is the concretization set of the abstract value
 * <p> NB: We assume that the empty string never occurs in the concretization set
 */
public class FactorCode {

  private GeneralizedSuffixTree tree;

  public FactorCode(@NotNull Set<Factor> factors) {
    this.tree = new GeneralizedSuffixTree(new ArrayList<>(factors), true);
  }

  public FactorCode(@NotNull ArrayList<Factor> factors) {
    this.tree = new GeneralizedSuffixTree(factors, true);
  }

  public FactorCode(@NotNull String str) {
    ArrayList<Factor> factors = new ArrayList<>();
    factors.add(new Factor(str));
    this.tree = new GeneralizedSuffixTree(factors, true);
  }

  public FactorCode(@NotNull Factor f) {
    ArrayList<Factor> factors = new ArrayList<>();
    factors.add(f);
    this.tree = new GeneralizedSuffixTree(factors, true);
  }

  public FactorCode(@NotNull GeneralizedSuffixTree tree) {
    this.tree = tree;
  }


  public static @NotNull FactorCode getTop() {
    Set<Factor> iset = new HashSet<>();
    return new FactorCode(iset);
  }

  public static @NotNull FactorCode getEmpty() {
    return new FactorCode(new HashSet<>());
  }

  public static @NotNull Set<Factor> addFactorToSet(@NotNull Set<Factor> ideals,
                                                    @NotNull Factor f) {
    boolean includes = false, is_included = false;
    Set<Factor> iset = new HashSet<>();
    for (Factor i : ideals) {
      if (!includes & i.contains(f)) {
        is_included = true;
        break;
      }
      if (!f.contains(i)) {
        iset.add(i);
      } else {
        includes = true;
      }
    }
    if (!is_included && !f.isEmpty()) {
      iset.add(f);
    }
    return is_included ? ideals : iset;
  }

  /**
   * Returns the set of all words of the length <b>len</b> in the given alphabet
   */
  public static @NotNull List<Factor> getAllWords(@NotNull Set<Character> alphabet, int len) {
    List<Factor> result = new ArrayList<>();
    if (len == 0) {
      result.add(new Factor());
      return result;
    }
    List<Factor> result_aux = getAllWords(alphabet, len - 1);
    for (var ch : alphabet) {
      for (var word : result_aux) {
        result.add(new Factor(ch.toString() + word.toString()));
      }
    }
    return result; //should this method be really placed in FactorCode?
  }

  public boolean isTop() {
    return (tree.getFactors().isEmpty());
  }

  public boolean isEmpty() {
    return tree.getFactors().isEmpty();
  }

  public int size() {
    return tree.getFactors().size();
  }

  public int commonLength() {
    int res = 0;
    for (Factor ir : tree.getFactors()) {
      res += ir.length();
    }
    return res;
  }

  public @NotNull FactorCode copy() {
    return new FactorCode(new HashSet<>(this.tree.getFactors()));
  }

  public @NotNull Set<Factor> getIdeals() {
    return new HashSet<>(this.tree.getFactors());
  }

  public @NotNull FactorCode addFactor(@NotNull Factor f) {
    if (f.isEmpty()) {
      return this;
    }
    GeneralizedSuffixTree newTree = tree.clearCopy();
    newTree.addFactor(f);
    return new FactorCode(newTree);
  }

  public @NotNull FactorCode addAllFactors(@NotNull FactorCode that) {
    ArrayList<Factor> allFactors = this.tree.getFactors();
    allFactors.addAll(that.tree.getFactors());
    return new FactorCode(allFactors);
  }

  public @NotNull FactorCode reverse() {
    Set<Factor> newideals = new HashSet<>();
    for (Factor ir : tree.getFactors()) {
      newideals.add(ir.reverse());
    }
    return new FactorCode(newideals);
  }

  public @NotNull FactorCode join(@NotNull FactorCode that) {
    ArrayList<Factor> mergeFactors = new ArrayList<>(this.tree.getFactors());
    int divider = mergeFactors.size() - 1;
    mergeFactors.addAll(that.tree.getFactors());
    GeneralizedSuffixTree commonTree = new GeneralizedSuffixTree(mergeFactors, false);
    ArrayList<Factor> lcss = commonTree.LCSSoddeven(divider);
    return new FactorCode(lcss);
  }

  public @NotNull FactorCode meet(@NotNull FactorCode that) {
    return this.addAllFactors(that);
  }

  /**
   * WiP method
   */
  public @NotNull FactorCode maxNonoverlap() {
    //Set<IdealRepresentation> allStr = getInternal();
    //IdealIntersection maxSubset;

    // for a while, works like all ideals do not overlap
    return this;
  }

  public @NotNull Set<Factor> superstrings(int len) {
    throw new UnsupportedOperationException("in progress");
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj instanceof FactorCode that) {
      return (this.tree.equals(that.tree));
    }
    return false;
  }

  @Override
  public int hashCode() {
    return tree.getFactors().hashCode();
  }

}
