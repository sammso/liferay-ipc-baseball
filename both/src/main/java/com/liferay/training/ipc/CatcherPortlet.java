package com.liferay.training.ipc;


import java.io.IOException;

import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ProcessEvent;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * Portlet implementation class CatcherPortlet
 */
public class CatcherPortlet extends GenericPortlet {

	public void init() {
		viewJSP = getInitParameter("view-jsp");
	}

	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

		include(viewJSP, renderRequest, renderResponse);
	}

	@ProcessEvent(qname = "{http://liferay.com/events}ipc.pitch")
	public void catchBall(EventRequest request, EventResponse response) {
		Event event = request.getEvent();
		String pitch = (String) event.getValue();
		System.err.println("Catching pitch " + pitch);
		response.setRenderParameter("pitch", pitch);
	}

	protected void include(String path, RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
			System.err.println(path + " is not a valid include");
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}

	protected String viewJSP;

}
