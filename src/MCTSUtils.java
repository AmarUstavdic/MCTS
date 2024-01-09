public class MCTSUtils {


    public static int depth(MCTSNode mctsNode) {
        return (mctsNode == null) ? 0 :
                (mctsNode.getChildren().isEmpty()) ? 1 :
                        1 + mctsNode.getChildren().stream().mapToInt(MCTSUtils::depth).max().orElse(0);
    }

}
