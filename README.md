##################################################################

THE CONTEXT

##################################################################


Sequence alignement is an important part of Bioinformatics. It is a necessary tool for
fields like phylogeny and for the "omics" methods.

##################################################################

THE PROJECT

##################################################################


This project aims to write a clustalW-based alignement program in Java.

The proximity between sequences and the order of alignment is assessed by UPGMA method
(Unweighted Pair Group Method with Arithmetic Mean). UPGMA allows to build a dendrogram
which is then used to guide sequence aligment.

Be aware that once sequences are in the same cluster, they will not be re-aligned against each other.

Input : several sequences to align in FASTA format.

Output : a graphical representation of the alignment.

##################################################################

USAGE

##################################################################


1) Download all the java files. The main() is in the Sequences class (with an "s")

2) In bash type : javac Sequences.java

3) Then type : java Sequences <your.fasta.file>
