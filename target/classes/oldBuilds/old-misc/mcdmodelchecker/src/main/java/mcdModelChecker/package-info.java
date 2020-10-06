/**
 * <b>Project Overview</b>
 * <p>This project is a <a href="https://en.wikipedia.org/wiki/Computation_tree_logic">CTL</a>
 * <a href="https://en.wikipedia.org/wiki/Model_checking">model checker</a> which implements some translation features
 * from the <a href="http://laser.cs.umass.edu/tools/littlejil.shtml">Little-JIL</a> process language.
 * <p>This project can process .ljx files (just xml files with a special file extension) outputted from the Little-JIL
 * plugin for the Eclipse IDE. These .ljx files are first visuallized in a graph where each node represents one xml tag.
 * Since the XML was generate using the Little-JIL process language, each node represents a Little-JIL control
 * structure, such as <code>sequential</code>, <code>parallel</code> or <code>leaf</code>.
 * <p>The XML is then translated according to Little-JIL status phases (posted, started, completed and terminated).
 * Each Little-JIL control structure has its own process of translation, so each control structure is translated
 * according to its specifications. The translated structure is then represented as a graph.
 * <p>Then any parallel nodes are again translated according to all their possible interleavings. If a parallel step has
 * four children, there will be <code>4!</code> or 24 different interleavings. This shows all the different paths
 * a parallel step can take. The expontential nature of the growth of the number of interleavings makes many of the
 * interleavings structures very large. The translation including the interleavings is then shown as a graph.
 * <p>This program takes the interleavings graph and represents it as a <a href="https://en.wikipedia.org/wiki/Kripke_structure_(model_checking)">
 * Kripke structure</a>. It then performs model checking on the Kripke structure, using computational tree logic, to
 * check if different properties (safety, liveness, etc), hold for the graph. If the property doesn't hold,
 * the program provides a counter example path. The program also displays some metrics on the process performed,
 * including how long it took to complete.
 */
package mcdModelChecker;