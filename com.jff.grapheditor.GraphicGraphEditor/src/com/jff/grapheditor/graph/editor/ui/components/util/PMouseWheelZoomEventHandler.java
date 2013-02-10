/*
 * Copyright (c) 2008-2011, Piccolo2D project, http://piccolo2d.org
 * Copyright (c) 1998-2008, University of Maryland
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 * and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * None of the name of the University of Maryland, the name of the Piccolo2D project, or the names of its
 * contributors may be used to endorse or promote products derived from this software without specific
 * prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jff.grapheditor.graph.editor.ui.components.util;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PComponent;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventFilter;



/**
 * <b>ZoomEventhandler</b> provides event handlers for basic zooming of the
 * canvas view with the right (third) button. The interaction is that the
 * initial mouse press defines the zoom anchor point, and then moving the mouse
 * to the right zooms with a speed proportional to the amount the mouse is moved
 * to the right of the anchor point. Similarly, if the mouse is moved to the
 * left, the the view is zoomed out.
 * <P>
 * On a Mac with its single mouse button one may wish to change the standard
 * right mouse button zooming behavior. This can be easily done with the
 * PInputEventFilter. For example to zoom with button one and shift you would do
 * this:
 * <P>
 * <code>
 * <pre>
 * zoomEventHandler.getEventFilter().setAndMask(InputEvent.BUTTON1_MASK | 
 *                                              InputEvent.SHIFT_MASK);
 * </pre>
 * </code>
 * <P>
 * 
 * @version 1.0
 * @author Jesse Grosjean
 */
/**
 * Zoom event handler that scales the camera view transform in response to mouse wheel events.
 *
 * @since 2.0
 */
public final class PMouseWheelZoomEventHandler extends PBasicInputEventHandler {
    /** Default scale factor, <code>0.1d</code>. */
    static final double DEFAULT_SCALE_FACTOR = 0.1d;

    /** Scale factor. */
    private double scaleFactor = DEFAULT_SCALE_FACTOR;

    /** Zoom mode. */
    private ZoomMode zoomMode = ZoomMode.ZOOM_ABOUT_MOUSE;


    /**
     * Create a new mouse wheel zoom event handler.
     */
    public PMouseWheelZoomEventHandler() {
        super();
        PInputEventFilter eventFilter = new PInputEventFilter();
        eventFilter.rejectAllEventTypes();
        eventFilter.setAcceptsMouseWheelRotated(true);
        setEventFilter(eventFilter);
    }


    /**
     * Return the scale factor for this mouse wheel zoom event handler.  Defaults to <code>DEFAULT_SCALE_FACTOR</code>.
     *
     * @see #DEFAULT_SCALE_FACTOR
     * @return the scale factor for this mouse wheel zoom event handler
     */
    public double getScaleFactor() {
        return scaleFactor;
    }

    /**
     * Set the scale factor for this mouse wheel zoom event handler to <code>scaleFactor</code>.
     *
     * @param scaleFactor scale factor for this mouse wheel zoom event handler
     */
    public void setScaleFactor(final double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    /**
     * Switch to zoom about mouse mode.
     *
     * @see ZoomMode#ZOOM_ABOUT_MOUSE
     */
    public void zoomAboutMouse() {
        zoomMode = ZoomMode.ZOOM_ABOUT_MOUSE;
    }

    /**
     * Switch to zoom about canvas center mode.
     *
     * @see ZoomMode#ZOOM_ABOUT_CANVAS_CENTER
     */
    public void zoomAboutCanvasCenter() {
        zoomMode = ZoomMode.ZOOM_ABOUT_CANVAS_CENTER;
    }

    /**
     * Switch to zoom about view center mode.
     *
     * @see ZoomMode#ZOOM_ABOUT_VIEW_CENTER
     */
    public void zoomAboutViewCenter() {
        zoomMode = ZoomMode.ZOOM_ABOUT_VIEW_CENTER;
    }

    /**
     * Return the zoom mode for this mouse wheel zoom event handler.  Defaults to
     * <code>ZoomMode.ZOOM_ABOUT_CANVAS_CENTER</code>.
     *
     * @return the zoom mode for this mouse wheel zoom event handler
     */
    ZoomMode getZoomMode() {
        return zoomMode;
    }

    /** {@inheritDoc} */
    public void mouseWheelRotated(final PInputEvent event) {
        PCamera camera = event.getCamera();
        double scale = 1.0d + event.getWheelRotation() * scaleFactor;
        Point2D viewAboutPoint = getViewAboutPoint(event);
        camera.scaleViewAboutPoint(scale, viewAboutPoint.getX(), viewAboutPoint.getY());
    }

    /**
     * Return the view about point for the specified event according to the current zoom mode.
     *
     * @param event input event
     * @return the view about point for the specified event according to the current zoom mode
     */
    private Point2D getViewAboutPoint(final PInputEvent event) {
        switch (zoomMode) {
            case ZOOM_ABOUT_MOUSE:
                return event.getPosition();
            case ZOOM_ABOUT_CANVAS_CENTER:
              /*  org.eclipse.swt.graphics.Rectangle canvasBounds = ((PSWTCanvasImproove) event.getComponent()).getBounds();
                Point2D canvasCenter = new Point2D.Double(canvasBounds.getCenterX(), canvasBounds.getCenterY());
                event.getPath().canvasToLocal(canvasCenter, event.getCamera());
                return event.getCamera().localToView(canvasCenter);*/
            case ZOOM_ABOUT_VIEW_CENTER:
                return event.getCamera().getBoundsReference().getCenter2D();
        }
        throw new IllegalArgumentException("illegal zoom mode " + zoomMode);
    }

    /**
     * Zoom mode.
     */
    enum ZoomMode {
        /**
         * Zoom about mouse mode.
         */
        ZOOM_ABOUT_MOUSE,

        /**
         * Zoom about canvas center mode.
         */
        ZOOM_ABOUT_CANVAS_CENTER,

        /**
         * Zoom about view center mode.
         */
        ZOOM_ABOUT_VIEW_CENTER;
    }
}