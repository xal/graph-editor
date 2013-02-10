package com.jff.grapheditor.graph.editor.ui.state;

import java.awt.geom.Point2D;

import com.jff.grapheditor.graph.editor.ui.GraphEditorComponent;
import com.jff.grapheditor.graph.editor.ui.components.objects.GraphEdge;
import com.jff.grapheditor.graph.editor.ui.components.objects.GraphLine;
import com.jff.grapheditor.graph.editor.ui.components.objects.GraphNode;

public class StateManager {

	public static AbstractState createNormalState() {
		AbstractState state = new NormalState();
		return state;
	}

	public static AbstractState createCreateEdgeState(GraphNode sourceNode) {
		AbstractState state = new CreateEdgeState(sourceNode);

		return state;

	}

	public static AbstractState createModifyEdgeState(GraphEdge graphEdge) {

		AbstractState state = new EditEdgeState(graphEdge);

		return state;
	}

}
