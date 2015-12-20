package husacct.analyse.task.reconstruct.bruteForce;

import java.util.ArrayList;
import java.util.Iterator;

// This MappingGenerator provides you with all the permutations by which a group of size N can be arranged into groups of size r.
// This is simply a multiplication of the number of combinations in one respect, the number of ways N can be split into groups of size r,
// times the number of combinations in the other respect, namely the number of ways a group of size r can be arranged.
// These permutations can be used for a "brute force" approach to pattern matching, where all possible mappings are considered.

public class MappingGenerator {

	private static int Slots;
	private static String[] PackageList;
	private static ArrayList<ArrayList<String>> permutations = new ArrayList<ArrayList<String>>();
	private static Iterator<ArrayList<String>> mappingIterator;

	public MappingGenerator(String[] packageList, int numberOfModules) {
		Slots = numberOfModules;
		PackageList = packageList;
		getPermutations();
		mappingIterator = permutations.iterator();
	}

	private static void addToPermutations(ArrayList<ArrayList<Integer>> arrayList) {
		for (int i = 0; i < arrayList.size(); i++) {
			ArrayList<String> temp = new ArrayList<String>(Slots);
			for (int j = 0; j < Slots; j++) {
				temp.add(PackageList[arrayList.get(i).get(j)]);
			}
			permutations.add(temp);
		}
	}

	public String[][] getPermutations() {
		combine(generatePackageNumbers());
		String[][] permutationsArray = new String[permutations.size()][];
		for (int i = 0; i < permutationsArray.length; i++) {
			permutationsArray[i] = permutations.get(i).toArray(new String[permutations.get(i).size()]);
		}
		return permutationsArray;
	}

	private int[] generatePackageNumbers() {
		int[] packageNumbers = new int[PackageList.length];
		for (int i = 0; i < PackageList.length; i++) {
			packageNumbers[i] = i;
		}
		return packageNumbers;
	}

	private void combine(int[] packageNumbers) {
		int[] combinations = new int[Slots];
		combineRecursive(packageNumbers, combinations, 0, 0, Slots);
	}

	private static void combineRecursive(int[] array, int[] result, int currentIndex, int level, int r) {
		if (level == r) {
			addToPermutations(permute(result));
			return;
		}
		for (int i = currentIndex; i < array.length; i++) {
			result[level] = array[i];
			combineRecursive(array, result, i + 1, level + 1, r);
			if (i < array.length - 1 && array[i] == array[i + 1]) {
				i++;
			}
		}
	}

	private static ArrayList<ArrayList<Integer>> permute(int[] combinations) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		result.add(new ArrayList<Integer>());
		for (int i = 0; i < combinations.length; i++) {
			ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>();
			for (ArrayList<Integer> l : result) {
				for (int j = 0; j < l.size() + 1; j++) {
					l.add(j, combinations[i]);
					ArrayList<Integer> temp = new ArrayList<Integer>(l);
					current.add(temp);
					l.remove(j);
				}
			}
			result = new ArrayList<ArrayList<Integer>>(current);
		}
		return result;
	}

	public ArrayList<String> next() {
		if (mappingIterator.hasNext()) {
			return (ArrayList<String>) mappingIterator.next();
		} else
			return null;
	}

	public ArrayList<String> getMapping(int i) {
		return (ArrayList<String>) permutations.get(i);
	}
}
