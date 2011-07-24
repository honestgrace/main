package devo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;


import com.google.common.collect.ImmutableList;

// Heapsort competes with quicksort, another very efficient nearly-in-place sort algorithm
// Quicksort is typically somewhat faster, due to better cache behavior and other factors.
// The worst-case running time for quicksort is makes it unacceptable for large data sets.
// Heapsort relies strongly on random access, and its poor locality of reference requires
// efficient random access to be practical.
// Mergesort is a stable sort, unlike quicksort and heapsort, and can be easily adapted
// to operate on linked lists and very large set on slow media with long access time.
// Heapsort requires only @(1) auxiliary space instead of Mergesort @(n) auxiliary space,
// quicksort with in-place partitioning and tail recursion uses only O(log n) space.

public class Oyster {
    public static class ARRAYS {
        // 1.1. Implement an algorithm to determine if a string has all unique characters.
        //      What if you can not use additional data structures?

        public static boolean hasNoDupCharsWithByBruteForce(String s) {
            for (int i = 0; i < s.length() - 1; i++) {
                for (int j = i + 1; j < s.length(); j++) {
                    if (s.charAt(i) == s.charAt(j)) {
                        return false;
                    }
                }
            }

            return true;
        }

        public static boolean hasNoDupCharsWithConstantSpace(String s) {
            BitSet bits = new BitSet(256);
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) >= 256) {
                    throw new IllegalArgumentException("'s' should contains no smaller than 256.");
                } else if (bits.get(s.charAt(i))) {
                    return false;
                } else {
                    bits.set(s.charAt(i));
                }
            }

            return true;
        }

        public static boolean hasNoDupCharsInLinearTime(String s){
            Set<Character> set = new HashSet<Character>();
            for (char c : s.toCharArray()) {
                if (set.contains(c)) {
                    return false;
                } else {
                    set.add(c);
                }
            }

            return true;
        }

        // 1.2. Write code to reverse a C-Style String.
        //      (C-String means that abcd is represented as five characters, including the null character).

        public static String reverse(String s) {
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length / 2; i++) {
                char save = chars[i];
                chars[i] = chars[chars.length - i];
                chars[chars.length - i] = save;
            }

            return new String(chars);
        }

        // 1.3. Design an algorithm and write code to remove the duplicate characters in a string without using any additional buffer.
        //      NOTE: One or two additional variables are fine. An extra copy of the array is not.
        //      FOLLOW UP: Write the test cases for this method.

        public static char[] removeDupCharsUsingStringBuilder(char[] input){
            if (input == null) return null;
            if (input.length < 2) return input;

            StringBuilder sb = new StringBuilder();
            Set<Character> set = new LinkedHashSet<Character>(); // LinkedHashSet keeps the order.
            for (char c : input){
                if (!set.contains(c)) {
                    set.add(c);
                    sb.append(c);
                }
            }
/*
            for (char c : set){
                sb.append(c);
            }
*/
            return sb.toString().toCharArray();
        }

        public static char[] removeDupCharsUsingArrayCopy(char[] chars){
            if (chars == null) return null;

            int length = 0;
            Set<Character> set = new HashSet<Character>(); // HashSet doesn't keep the order.
            for (char c : chars) {
                if (!set.contains(c)) {
                    set.add(c);
                    chars[length++] = c;
                }
            }

            return Arrays.copyOfRange(chars, 0, length);
        }
        
        public static char[] removeDupCharsUsingBitSet(char[] chars){
            BitSet bits = new BitSet(256);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] >= 256) {
                    throw new IllegalArgumentException("'chars' should contains no smaller than 256.");
                } else if (!bits.get(chars[i])) {
                    bits.set(chars[i]);
                    sb.append(chars[i]);
                }
            }
            return sb.toString().toCharArray();
        }

        // 1.4. Write a method to decide if two strings are anagrams or not.
        public static boolean anagrams(String s, String t) {
            if (s == t) return true;
            if (null == s || null == t) return false;
            if (s.length() != t.length()) return false;

            Map<Character, Integer> hits = new HashMap<Character, Integer>();
            for (char c : s.toCharArray()) {
                // hits.put(c, 1 + (hits.containsKey(c) ? hits.get(c) : 0));
                if (hits.containsKey(c)) {
                    hits.put(c, hits.get(c) + 1);
                } else {
                    hits.put(c, 1);
                }
            }

            for (char c : t.toCharArray()) {
                if (hits.containsKey(c)) {
                    hits.put(c, hits.get(c) - 1);
                } else {
                    return false;
                }
            }

            for (int i : hits.values()) {
                if (0 != i) {
                    return false;
                }
            }

            return true;
        }

        // 1.5. Write a method to replace all spaces in a string with '%20'.

        public static String escapeSpaces(String s) {
            StringBuilder sb = new StringBuilder();
            for (char c : s.toCharArray()) {
                if (' ' == c) {
                    sb.append("%20");
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        }

        // 1.6. Given an image represented by an NxN matrix, where each pixel in the image is 4 bytes, 
        //      write a method to rotate the image by 90 degrees. Can you do this in place?

        public static void rotate90Degrees(int[][] image) { // integer has 4 bytes on most platforms.
            for (int layer = 0; layer < image.length/2; layer++) {
                int first = layer;
                int last = image.length - 1 - layer;
                for (int i = first; i < last; i++){
                    int offset = i - first;
                    int top = image[first][i];  //save top
                    //top <- left
                    image[first][i] = image[last-offset][first];
                    //left <- bottom
                    image[last-offset][first] = image[last][last - offset];
                    //bottom <- right
                    image[last][last-offset] = image[i][last];
                    //right <- top
                    image[i][last] = top;
                }
            }
        }

        // 1.7. Write an algorithm such that if an element in an MxN matrix is 0, its entire row and column is set to 0.
        public static void zeroOutRowsAndColumns(int[][] matrix){
            BitSet rows = new BitSet(matrix.length);
            BitSet columns = new BitSet(matrix[0].length);
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (columns.length() != matrix[i].length) {
                        throw new IllegalArgumentException("'matrix' must be a rectable.");
                    }

                    if (matrix[i][j] == 0) {
                        rows.set(i);
                        columns.set(i);
                    }
                }
            }

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (rows.get(i) || columns.get(j)) {
                        matrix[i][j] = 0;
                    }
                }
            }
        }

        // 1.8. Assume you have a method isSubstring which checks if one word is a substring of another.
        //      Given two strings, s1 and s2, write code to check if s2 is a rotation of s1 using only one call to isSubstring
        //      (i.e., "waterbottle" is a rotation of "erbottlewat").
        public static boolean rotated(String s1, String s2){
            if (s1.length() != s2.length()) {
                return false;
            } else {
                return (s1 + s1).contains(s2);
            }
        }

        // 1-A. Find the max sum sub array.
        //      Kadane's algorithm http://en.wikipedia.org/wiki/Maximum_subarray_problem
        public static int[] findMaxSumSubArrayUsingKadane(int... a) {
            int maxHead = 0, maxTail = 0, maxSum = a[0];
            for (int head = 0, tail = 1, sum = a[0]; tail < a.length; tail++) {
                sum = sum + a[tail];
                if (sum > maxSum) {
                    maxHead = head;
                    maxTail = tail;
                    maxSum = sum;
                }

                if (0 > sum) {
                    head = tail + 1;
                    sum = 0;
                }
            }

            return new int[] { maxSum, maxHead, maxTail };
        }

        // 1-B. Find the modes using a map in Java (also called Dictionary in C#).
        // modes : the most frequent numbers
        public static List<Integer> findModesUsingMap(int... numbers) {
            // We assert preconditions; arguments according to assumptions (for debug builds).
            assert null != numbers;

            // We validate arguments if this is a public interface (for debug and retail builds).
            if (null == numbers) {
                throw new NullPointerException("'numbers' must be non-null.");
            }

            // We short-circuit known cases such as an empty set of numbers.
            if (0 == numbers.length) {
                return ImmutableList.of();
            }

            int maximumHits = 1;
            List<Integer> modes = new ArrayList<Integer>();
            Map<Integer, Integer> hitsByNumber = new HashMap<Integer, Integer>();
            for (int number : numbers) {
                if (hitsByNumber.containsKey(number)) {
                    hitsByNumber.put(number, 1 + hitsByNumber.get(number));
                } else {
                    hitsByNumber.put(number, 1);
                }

                if (hitsByNumber.get(number) >= maximumHits) {
                    if (hitsByNumber.get(number) > maximumHits) {
                        maximumHits = hitsByNumber.get(number);
                        modes.clear();
                    }

                    modes.add(number);
                }
            }

            // We assert postconditions.
            assert !modes.isEmpty() : modes;
            return modes;
        }

        // 1-B2. Find the modes using a map in Array (also called Dictionary in C#).
        public static List<Integer> findModesUsingArray(int... numbers) {
            // We assert preconditions; arguments according to assumptions (for debug builds).
            assert null != numbers;

            // We validate arguments if this is a public interface (for debug and retail builds).
            if (null == numbers) {
                throw new NullPointerException("'numbers' must be non-null.");
            }

            // We short-circuit known cases such as an empty set of numbers.
            if (0 == numbers.length) {
                return ImmutableList.of();
            }

            int min = numbers[0];
            int max = numbers[0];
            for (int i = 1; i < numbers.length; i++) {
                min = Math.min(min, numbers[i]); // 1, 2, 2, 3, 3, 3
                max = Math.max(max, numbers[i]);
            }

            int maximumHits = 1;
            List<Integer> modes = new ArrayList<Integer>();

            int[] hitsByNumber = new int[max - min + 1]; // 11, 10..20 -> 10 rooms
            for (int number : numbers) {
                hitsByNumber[number - min]++; // {0, 0, ... } => {0, 1, ... }
                if (hitsByNumber[number - min] >= maximumHits) {
                    if (hitsByNumber[number - min] > maximumHits) {
                        modes.clear();
                        maximumHits = hitsByNumber[number - min];
                    }

                    modes.add(number);
                }
            }

            // assert !modes.isEmpty() : modes;
            return modes;
        }

        // 1-C. Find a set which sums up to x given integer array.
        //      Input: x = 6, a = { 5, -1, 1, -2, 4, 7, 2 }
        //      Output: {5, 1}, {-1, 7}, {4, 2}
        public static List<Integer> findPairsOfSumXByBruteForce(int x, int[] a) {
            if (a == null) throw new NullPointerException("'a' must be non-null.");
            if (a.length < 2) return ImmutableList.of(); // or, Collections.emptyList()

            List<Integer> output = new ArrayList<Integer>();
            for (int i = 0; i < a.length - 1; i++) {
                for (int j = i + 1; j < a.length; j++) {
                    if (a[j] + a[i] == x) {
                        output.add(a[i]);
                        output.add(a[j]);
                    }
                }
            }

            return output;
        }
        
        // 1 Find all non repeated characters (unique characters)
        public static String findNonRepeated(String s){
            Map<Character, Object> map = new HashMap<Character, Object>();
            Object seenOnce = null;
            Object seenTwice = null;
            for (char c : s.toCharArray()){
                if(map.containsKey(c)){
                    map.put(c, seenTwice);
                }
                else {
                    map.put(c, seenOnce);
                }
            }
            
            List<Character> l = new ArrayList<Character>();
            for (char c : s.toCharArray()){
                if(map.get(c) == seenOnce){
                    l.add(c);
                }
            }
            return l.toString();
            //return Arrays.toString(l.toArray());
        }

        // 1-C2. Find a set which sums up to x given integer array using hash set
        public static List<Integer> findPairsOfSumXByHashing(int x, int[] a) {
            if (a == null) throw new NullPointerException("'a' must be non-null.");
            if (a.length < 2) return ImmutableList.of(); // or, Collections.emptyList()

            List<Integer> output = new ArrayList<Integer>();
            Set<Integer> set = new HashSet<Integer>();
            for (int i : a) {
                if (set.contains(x - i)) {
                    output.add(i);
                    output.add(x - i);
                } else {
                    set.add(i);
                }
            }

            return output;
        }

        // 1-D. Find a sub array that sums up to x given integer array.
        //      Input: x = 6, a = { 5, -1, 3, -2, 5, -3, 4, 2 }
        //      Output: a[2] .. a[4] when you see 3 - 2 + 5 = 6.
        public static int[] findSubArrayOfSumXInLinearTime(int x, int... a) {
            return findSubArrayOfSumXPrivate(x, a);
        }

        private static int[] findSubArrayOfSumXPrivate(int x, int[] a) {
            int[] prefixSums = new int[a.length]; // prefixSum[i] has the sum of a[0..i]
            prefixSums[0] = a[0];
            for (int i = 1; i < a.length; i++) {
                prefixSums[i] = prefixSums[i - 1] + a[i];
            }

            // map has a prefix sum to look for, and the lower bound to return.
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            for (int i = 0; i < a.length; i++) {
                if (x == prefixSums[i]) {
                    return new int[] { 0, i };
                } else if (map.containsKey(prefixSums[i])) {
                    return new int[] { map.get(prefixSums[i]), i };
                } else {
                    map.put(x + prefixSums[i], i + 1);
                }
            }

            return null;
        }
        //1-E Find and Replace a string
        public static String FindReplace(String input, String find, String replace){
            if (input.length() < find.length()) {
                return input;
            }
            
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < input.length();){
                if(!StartAt(input, i, find)){
                    sb.append(input.charAt(i++));
                }
                else{
                    sb.append(replace);
                    i += find.length();
                }
            }
            return sb.toString();
        }
        
        public static Boolean StartAt(String input, int offset, String find){
            for (int i = 0; i < find.length(); i++){
                if (offset + i < input.length() && input.charAt(offset + i) != find.charAt(i)){
                    return false;
                }
            }
            return true;
        }
    }

    public static class LinkedLists {
        public static class SNode implements Comparable<SNode> {
            public int data;
            public SNode next;

            public SNode() {
            }

            public SNode(int data) {
                this.data = data;
                this.next = null; // this line seems unnecessary, as the uninitialized fields remain to be null.
            }

            public static SNode snodeOf(int data) {
                SNode snode = new SNode();
                snode.data = data;
                return snode;
            }

            public static SNode snodeOf(int data, SNode next) {
                SNode snode = new SNode();
                snode.data = data;
                snode.next = next;
                return snode;
            }

            // 2.1. Write code to remove duplicates from an unsorted linked list. 
            //      FOLLOW UP: How would you solve this problem if a temporary buffer is not allowed?
            public static SNode removeDupsInConstantSpace(SNode head) { // in O(n*n) time
                for (SNode remaining = head; remaining != null; remaining = remaining.next) {
                    SNode previous = remaining;
                    SNode current = remaining.next;

                    for (; null != current; current = current.next) {
                        if (remaining.data == current.data) {
                            previous.next = current.next; // previous remains to be the same.
                        } else {
                            previous = current;
                        }
                    }
                }

                return head;
            }

            // This method removes duplicates using a hash map.
            public static SNode removeDupsInLinearTime(SNode head) { // in O(n) space
                Set<Integer> set = new HashSet<Integer>();
                SNode previous = null;
                SNode current = head;
                while (current != null) { 
                    if (set.contains(current.data)) {
                        previous.next = current.next;
                    } else {
                        set.add(current.data);
                        previous = current;
                    }

                    current = current.next;
                }

                return head;
            }

            // 2.2.1. Implement an algorithm to find the n-th to last element of a singly linked list.
            public static SNode nthToLast(SNode head, int n){
                if (head == null) throw new IllegalArgumentException("'head' must be non-null.");
                if (n <= 0) throw new IllegalArgumentException("'n' must be positive.");

                SNode p1 = head;
                SNode p2 = head;

                for (int i = 0; i < n - 1; i++) {
                    if (p2 == null)
                        return null;
                    p2 = p2.next;
                }

                while (p2.next != null) {
                    p1 = p1.next;
                    p2 = p2.next;
                }

                return p1;
            }

            // 2.2.2. Implement an algorithm to find the n-th to last element of a singly linked list.
            public static SNode nthToLastInOneScan(SNode head, int n) {
                if (head == null || n < 1) {
                    return null;
                }

                Queue<SNode> q = new LinkedList<SNode>();
                while (head != null) {
                    if (q.size() > n) {
                        q.remove(); // dequeue
                    } else {
                        q.add(head); // enqueue
                    }

                    head = head.next;
                }

                return q.size() < n ? null : q.peek();
            }

            // 2.3. Implement an algorithm to delete a node in the middle of a single linked list, given only access to that node.
            //      EXAMPLE: Input: the node c from the linked list a->b->c->d->e
            //               Result: nothing is returned, but the new linked list looks like a->b->d->e
            //      Look at 2.B
            
            // 2.4. You have two numbers represented by a linked list, where each node contains a single digit.
            //      The digits are stored in reverse order, such that the 1's digit is at the head of the list.
            //      Write a function that adds the two numbers and returns the sum as a linked list.
            //      EXAMPLE: Input: (3 -> 1 -> 5), (5 -> 9 -> 2)
            //               Output: 8 -> 0 -> 8

            public static SNode sumDigitNodes(SNode lhs, SNode rhs) {
                int sum = 0;
                for (int d = 1; lhs != null || lhs != null; d *= 10) {
                    if (lhs != null) {
                        sum += d * lhs.data;
                        lhs = lhs.next;
                    }

                    if (rhs != null) {
                        sum += d * rhs.data;
                        rhs = rhs.next;
                    }
                }

                SNode current = new SNode(sum % 10);
                SNode output = current;
                sum /= 10;
                for (; sum > 0; current = current.next) {
                    current.next = new SNode(sum % 10);
                    sum /= 10;
                }

                return output;
            }

            // 2.5. Given a circular linked list, implement an algorithm which returns node at the beginning of the loop.
            //      DEFINITION: Circular linked list: A (corrupt) linked list in which a nodes next pointer points to an earlier node, so as to make a loop in the linked list.
            //      EXAMPLE: Input: A -> B -> C -> D -> E -> C [the same C as earlier]
            //      Output: C

            public static SNode removeCycleInSingleLinkedList(SNode current) {
                SNode p1 = current; // walk at normal speed
                SNode p2 = current; // walk at double speed
                SNode joint = current; // first node of the cycle
                SNode chain = null; // chain node to fix up

                while (p2 != null && p2.next != null) {
                    p2 = p2.next.next;
                    p1 = p1.next;
                    if (p1 == p2)
                        break;
                }

                if (p2 == null || p2.next == null) return null; 

                while (p1 != joint) {
                    joint = joint.next;
                    chain = p1;
                    p1 = p1.next;
                }

                chain.next = null;
                return joint;
            }
            
            
            // 2.A This method inserts an data element into a sorted linked list, and returns the new head node.
            public static SNode insert(SNode head, int insert) {
                return insertIntoSorted(head, SNode.snodeOf(insert));
            }

            private static SNode insertIntoSorted(SNode head, SNode insert) {
                // how-to assert in Java http://download.oracle.com/javase/1.4.2/docs/guide/lang/assert.html
                assert null != insert : insert;

                if (head == null) {
                    return insert;
                }

                if (head.data > insert.data) {
                    insert.next = head;
                    return insert;
                }

                for (SNode previous = head; ; previous = previous.next) {
                    if (previous.next == null || previous.next.data >= insert.data) { // repeat until ...
                        insert.next = previous.next;
                        previous.next = insert;
                        break;
                    }
                }

                // Henry's proposal for fix as follows:
//                SNode previous = head;
//                for (; previous.next != null && previous.next.data < insert.data; previous = previous.next) {
//                }
//
//                insert.next = previous.next;
//                previous.next = insert;

                return head;
            }

            
            // 2.B This method deletes an data element into a sorted linked list, and returns the new head node.
            public static SNode deleteOne(SNode head, int delete){
                if (head == null) return head;
                if (head.data == delete) return head.next;

                for (SNode current = head; current.next != null; current = current.next){
                    if (current.next.data == delete) {
                        current.next = current.next.next;
                        break;
                    }
                }

                return head;
            }

            //       This method deletes a node in a constant time, i.e. time O(1), space: O(1)
            public static void deleteInConstantTime(SNode delete) {
                if (delete.next != null) {
                    delete.data = delete.next.data;
                    delete.next = delete.next.next;
                } else {
                    delete.data = 0; // mark it deleted and collect garbage later on
                }
            }

            
            // 2.C This method reverses a linked list in an iteration.
            public static SNode reverse(SNode current) {
                SNode result = null;
                while (current != null) {
                    SNode save = current.next;
                    current.next = result;
                    result = current;
                    current = save;
                }

                return result;
            }
            // 2.D . Merge sort in linked list.
            public SNode mergeSort(SNode p) {
                if (null == p || null == p.next)
                    return p;
                SNode q = partition(p);
                p = mergeSort(p);
                q = mergeSort(q);
                p = merge(p, q);
                return p;
            }

            public SNode partition(SNode p) {
                SNode p1 = p;
                SNode p2 = p.next;
                while (null != p2 && null != p2.next) {
                    p2 = p2.next.next;
                    p2 = p1.next;
                }

                SNode q = p1.next;
                p1.next = null;
                return q;
            }

            public SNode merge(SNode p, SNode q) {
                SNode head = null, r = null;
                while (null != p && null != q) {
                    SNode next;
                    if (p.compareTo(q) < 0) {
                        next = p;
                        p = p.next;
                    } else {
                        next = q;
                        q = q.next;
                    }

                    if (null == head) {
                        head = r = next;
                    } else {
                        r.next = next;
                        r = r.next;
                    }
                }

                if (null == p)
                    r.next = q;
                if (null == q)
                    r.next = p;
                return head;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (SNode n = this; null != n; n = n.next) {
                    sb.append("{").append(n.data).append("}, ");
                }

                return sb.substring(0, sb.length() - 2);
            }

            @Override
            public int compareTo(SNode o) {
                return this.data - o.data;
            }
        }

        // This class represents a doubly linked list node.
        public static class DNode {
            public int data;
            public DNode next;
            public DNode prev;

            public DNode() {
            }

            public DNode(int data) {
                this.data = data;
                this.next = null; 
                this.prev = null;
            }

            public static DNode dnodeOf(int data) {
                DNode dnode = new DNode();
                dnode.data = data;
                return dnode;
            }

            public static DNode dnodeOf(int data, DNode prev) {
                DNode dnode = new DNode();
                dnode.data = data;
                dnode.prev = prev;
                dnode.next = null;
                return dnode;
            }

            // 2.E Insert a node to a sorted doubly linked list
            public static DNode insertIntoSorted(DNode head, DNode insert){
                if (head == null) return insert;
                if (insert == null) return head;

                if (head.data >= insert.data) {
                    insert.prev = null; // Glitch fixed by Henry
                    insert.next = head;
                    head.prev = insert;
                    return insert;
                }

                DNode current = head;
                for (; current.next != null && current.next.data < insert.data; current = current.next) {
                }

                insert.prev = current;
                insert.next = current.next;
                if (insert.next != null) {
                    insert.next.prev = insert;
                }

                current.next = insert;
                return head;
            }

            // 2.F This method deletes a node from a doubly linked list.
            public static DNode deleteOneBuggy(DNode head, int delete){
                if (head == null) return null;
                if (head.data == delete) return head.next; // buggy, when to delete 1 from null -> 1 -> 1 -> null.

                // buggy, b/c you cannot delete 9 if the list ends with 9 -> null.
                for (DNode current = head; current.next != null; current = current.next){
                    if (current.next.data == delete) {
                        current.next = current.next.next;
                        if (current.next != null)
                            current.next.prev = current;
                    }
                }

                return head;
            }

            // This method deletes nodes from a doubly linked list.
            public static DNode deleteAll(DNode head, int delete){
                for ( ; delete == head.data; head = head.next, head.prev = null) {
                }

                for (DNode current = head; current != null; current = current.next){
                    if (delete == current.data) {
                        if (null != current.prev) {
                            current.prev.next = current.next;
                        }

                        if (null != current.next) {
                            current.next.prev = current.prev;
                        }
                    }
                }

                return head;
            }

            // 2.G Reverse the doubly linked list.
            public static DNode reverse(DNode current) {
                if (null == current) throw new NullPointerException("current");

                while (null != current.next) {
                    DNode save = current.next;
                    current.next = current.prev;
                    current.prev = save;
                    current = save;
                }

                return current;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                for (DNode n = this; null != n; n = n.next) {
                    sb.append("{").append(n.data).append("}, ");
                }

                return sb.substring(0, sb.length() - 2);
            }
        }
    }

    // Chapter 3 Stacks and Queues
    public static class Stacks {
        // 3.2 Push, pop, min
        public static class MinStack<T extends Comparable<T>> {
            private T mininum;
            private Stack<T> stack = new Stack<T>();

            public T getMin() {
                return mininum;
            }

            public MinStack<T> push(T element) {
                if (null == mininum || element.compareTo(mininum) <= 0) {
                    stack.push(mininum);
                    mininum = element;
                }

                stack.push(element);
                return this;
            }

            public T pop() {
                T element = stack.pop();
                if (element.compareTo(mininum) == 0) {
                    mininum = stack.pop();
                }

                return element;
            }
        }
        
        // 3.5 Implement Queue using 2 stacks
        public static class Queueable<T> {
            Stack<T> stk1 = new Stack<T>();
            Stack<T> stk2 = new Stack<T>();

            void push(T e) {
                stk1.push(e);
            }

            T pop() {
                if (stk2.size() > 0) {
                    while (stk1.size() == 0) {
                        stk2.push(stk1.pop());
                    }
                }

                T e = stk2.pop();
                return e;
            }
        }
    }

    public static class Recursions {
        
        // 8.1 Write a method to generate the nth Fibonacci number.
        // 0, 1, 1, 2, 3, 5, 8, 13, 21, 34
        // F0 = 0, F1 = 1
        public static int fibo(int index){
            if (index < 0) {
                return -1;
                //throw new IllegalArgumentException("'index' must be positive.");
            }
            if (index == 0) return 0;
            if ( index < 3) return 1;
            int number = 0;
            int oneback = 1;
            int twoback = 1;
            for (int i = 3; i <= index; i++){
                number = oneback + twoback;
                twoback = oneback;
                oneback = number;
            }
            return number;
        }
        
        public static int fiboUsingRecursion(int index){
            if (index < 0) return -1;
            else if (index == 0) return 0;
            else if (index == 1 || index == 2) return 1; 
            else return fiboUsingRecursion(index - 1) + fiboUsingRecursion(index - 2);
        }
        
        //8.A To/From Excel Column 
        //Need Clarification for the problem
        //   26 cases :   A -   Z
        // 26^2 cases :  AA -  ZZ
        // 26^3 cases : AAA - ZZZ
        public static String toExcelColumn(int n) {
            int cases = 26;
            int k = 0;
            for (; n > cases; k++) { // k is the level of recursion.
                n -= cases;
                cases *= 26;
            }

            n -= 1; // n falls between 0 and (26^k - 1).
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <= k; i++) {
                sb.insert(0, (char) (n % 26 + 'A'));
                n /= 26;
            }

            return sb.toString();
        }

        public static int fromExcelColumn(String s) {
            int columns = 0;
            for (int k = 1, cases = 26; k < s.length(); k++, cases *= 26) {
                columns += cases;
            }

            for (int k = s.length() - 1, cases = 1; k >= 0; k--, cases *= 26) {
                columns += cases * (int) (s.charAt(k) - 'A');
            }

            return columns + 1;
        }
    }

    public static class Sorting {
        // 9.1 You are given two sorted arrays, A and B, and A has a large enough buffer at the end to hold B.
        //     Write a method to merge B into A in sorted order.
        public static void merge(int[] a, int[]b, int n, int m){
            int k = n + m - 1; 
            int i = n -1;
            int j = m -1;
            while (i >= 0 && j >=0){
                if (a[i] > b[j]){
                    a[k--] = a[i--];
                }
                a[k--] = b[j--];
            }
            
            while (i >= 0){
                a[k--] = a[i--];
            }
        }
        
        
        // Merge Sort - sort each pair of elements. Then, sort every four elements by merging every two pairs. 
        //              Then, sort every 8 elements, etc. O(n log n) expected and worst case.
        public static void mergeSort(int[] elements) {
            int[] temp = new int[elements.length];
            mergeSort(elements, temp, 0, elements.length - 1);
        }

        private static void mergeSort(int[] input, int[] temp, int left, int right) {
            if (left < right) {
                int center = (left + right) / 2;
                mergeSort(input, temp, left, center);
                mergeSort(input, temp, center + 1, right);
                merge(input, temp, left, center + 1, right);
            }
        }

        private static void merge(int[] input, int[] temp, int left, int right, int rightEnd) {
            int leftEnd = right - 1;
            int leftBegin = left;
            int position = 0;
            while (left <= leftEnd && right <= rightEnd) {
                if (input[left] <= input[right]) {
                    temp[position++] = input[left++];
                } else {
                    temp[position++] = input[right++];
                }
            }

            while (left <= leftEnd) {
                temp[position++] = input[left++];
            }

            while (right <= rightEnd) {
                temp[position++] = input[right++];
            }

            System.arraycopy(temp, 0, input, leftBegin, rightEnd - leftBegin + 1);
        }
        
        // 9.2 Write a method to sort an array of strings so that all the anagrams are next to each other.
        
/*        public class AnagramComparator implements Comparator<String>{
            public String sortChars(String s){
                char[] content = s.toCharArray();
                Arrays.sort(content);
                Arrays.s
                return new String(content);
            }
            public int compare(String s1, String s2){
                return sortChars(s1).compareTo(sortChars(s2));
            }
        }
        Arrays.sort(char[] array, new AnagramComparator());------>????*/
        
        // 9.3 Given a sorted array of n integers that has been rotated an unknown number of times, 
        //     give an O(log n) algorithm that finds an element in the array.
        //     You may assume that the array was originally sorted in increasing order.
        //      EXAMPLE:
        //      Input: find 5 in array (15 16 19 20 25 1 3 4 5 7 10 14)
        //      Output: 8 (the index of 5 in the array)
        
        public static int indexOutOfCycle(int i, int... args) {
            if (null == args)
                throw new IllegalArgumentException("'args' must be non-null.");
            if (0 == args.length)
                throw new IllegalArgumentException("'args' must not be empty.");

            return indexOutOfCycle(0, args.length - 1, i, args);
        }

        private static int indexOutOfCycle(int left, int right, int i, int... args) {
            int pivot = (left + right) / 2;
            if (i == args[pivot]) {
                return pivot;
            } else if (left == right) {
                return -1;
            } else if (i == args[right]) {
                return right;
            }

            if (args[right] < args[pivot]) {
                if (args[right] < i && i < args[pivot]) {
                    return indexOutOfCycle(left, pivot - 1, i, args);
                } else {
                    return indexOutOfCycle(pivot + 1, right - 1, i, args);
                }
            } else {
                if (args[pivot] < i && i < args[right]) {
                    return indexOutOfCycle(pivot + 1, right - 1, i, args);
                } else {
                    return indexOutOfCycle(left, pivot - 1, i, args);
                }
            }
        }
        // Find the smallest number in the rotated array
        //      Input: find the smallest number in array (15 16 19 20 25 1 3 4 5 7 10 14)
        //      Output: 1 
        public static int smallestOutOfCycle(int... args) {
            if (null == args)
                throw new IllegalArgumentException("'args' must be non-null.");
            if (0 == args.length)
                throw new IllegalArgumentException("'args' must not be empty.");

            return smallestOutOfCycle(0, args.length - 1, args);
        }

        private static int smallestOutOfCycle(int left, int right, int... args) {
            if (right == left) {
                return args[left];
            } else {
                int pivot = (left + right) / 2;
                if (args[right] < args[pivot]) {
                    return smallestOutOfCycle(pivot + 1, right, args);
                } else {
                    return smallestOutOfCycle(left, pivot, args);
                }
            }
        }
    }


    public static class Mix {
        // Find angle between hour and minute
        public static int getAngle(int hour, int min){
            int angle = 0;
            if (hour < 0 || hour > 11){
                throw new IllegalArgumentException("'hour' should be between 0 ~ 11");
            }
            if (min < 0 || min > 59){
                throw new IllegalArgumentException("'min' should be between 0 ~ 59");

            }

            angle = 360 * hour/12 - 360 * min / 60 ; // (30 * min/60);
            angle = (angle + 360) % 360 ;
            return angle > 180 ? angle - 180 : angle;
        }
        
        public static int weighedChoice(int... weights) { // new int[] { 20, 30, 50 }, or 20, 30, 50
            int sum = 0;
            for (int i = 0; i < weights.length; i++) {
                sum += weights[i];
            }

            int random = new Random().nextInt(sum); // a random out of [0 .. sum - 1]
            int i = 0;
            for (; i < weights.length && random >= weights[i]; i++, random -= weights[i]) {
            }

            return i;
        }

        public static void knuthShuffle(int[] a) {
            Random random = new Random();
            for (int i = 0; i < a.length; i++) {
                int j = i + random.nextInt(a.length - i); // j is between i and length - 1
                int save = a[j];
                a[j] = a[i];
                a[i] = save;
                System.out.print(a[i]);
            }
        }

        public static List<Integer> reservoirSamples(Iterator<Integer> iterator, int k) {
            List<Integer> samples = new ArrayList<Integer>();
            int count = 0;
            Random random = new Random();
            while (iterator.hasNext()) {
                count++;
                if (samples.size() < k) {
                    samples.add(iterator.next());
                } else {
                    int s = random.nextInt(count);
                    if (s < k) {
                        samples.set(s, iterator.next());
                    }
                }
            }

            return samples;
        }

        public static List<Integer> sampleKOutOfN(int n, int k) {
            List<Integer> samples = new ArrayList<Integer>();
            Random random = new Random();
            while (samples.size() < k) {
                int x = random.nextInt(n); // x : 0 ~ n-1
                if (!samples.contains(x)) {
                    samples.add(x);
                }
            }

            return samples;
        }

        public static void PrintHex(int i){
            if ( i < 0){
                System.out.print('-');
                i = -i;
            }
            System.out.print("0x");
            printHex(i);
        }
        
        public static void printHex(int i){
            if ( i >= 16){
                printHex(i/16);
                printHex(i%16);
            }
            else{
                System.out.print(i < 10 ? (char)((int)'0' + i) :(char)((int)'A' + i - 10));
            }
        }
        
        public static void PrintHexFast(int i){
            if ( i < 0){
                System.out.print('-');
                i = -i;
            }
            System.out.print("0x");
            Stack<Integer> s = new Stack<Integer>();
            s.push(i);
            while(s.size() > 0){
                i = s.pop();
                if (i >= 16){
                    s.push(i % 16);
                    s.push(i / 16);
                }
                else{
                    System.out.print(i < 10 ? (char)((int)'0' + i) : (char)((int)'A'+ i - 10));
                }
            }
            
        }
        
        public static int reverseInt(int number){
            int reverse = 0;
            while (number != 0 ){
                reverse = reverse * 10 + number % 10;
                number = number / 10;
            }
            return reverse;
        }
        
        public static int rand7() {
            int rand21;
            do {
                rand21 = 5 * (rand5() - 1); // 0, 5, ..., 20.
                rand21 += rand5(); // 1, 2, ..., 5.
            } while (rand21 > 21);

            assert rand21 >= 1 && rand21 <= 21;
            return rand21 % 7 + 1; // 1, 2, ..., 7
        }

        public static int rand5() {
            return new Random().nextInt() % 5 + 1;
        }

        public static void swap(char[] chars, int i, int j) {
            if (chars[i] != chars[j]) {
                chars[i] ^= chars[j];
                chars[j] ^= chars[i];
                chars[i] ^= chars[j];
            }
        }

        public static void swap(int[] ints, int i, int j) {
            if (ints[i] != ints[j]) {
                ints[i] ^= ints[j];
                ints[j] ^= ints[i];
                ints[i] ^= ints[j];
            }
        }
    }
}