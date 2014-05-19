package com.project.memecollector;

import java.util.ArrayList;
import java.util.List;

import com.project.memecollector.data.Meme;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Title;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Title("Main App")
@Push
public class Main extends UI {
	
	private static final long serialVersionUID = 1L;
	
	private List<Meme> database = new ArrayList<Meme>();
	private VerticalLayout vl = new VerticalLayout();
	private VerticalLayout vl2 = new VerticalLayout();
	private HorizontalLayout hl = new HorizontalLayout();
	
	
	@Override
	protected void init(VaadinRequest request) {
		initLayout();
//		initForm();
//		initTable();
		final TextField tf = new TextField();
		Button bt = new Button("click me");
		final Label lbl = new Label("Hello");
		Table table = new Table("My table");
		
		table.addContainerProperty("name", String.class, null);
		table.addContainerProperty("url", Link.class, null);
		table.addContainerProperty("action", Button.class, null);
		
		
		table.addItem("row1").getItemProperty("name").setValue("some given value"); // Create item by explicit ID
		Link tmp = new Link("", new ExternalResource("http://google.com"));
		tmp.setIcon(new ExternalResource("http://staticpics2.allegrostatic.pl/showcase/sh_90540"));
		table.getItem("row1").getItemProperty("url").setValue(tmp);
		table.addItem("row2").getItemProperty("name").setValue("another given value");
		
		vl.addComponent(tf);
		vl.addComponent(bt);
		vl.addComponent(lbl);
		hl.addComponent(vl);
		vl2.addComponent(table);
		hl.addComponent(vl2);
		
		setContent(hl);
		
		bt.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				String message = tf.getValue();
				lbl.setValue(message);			
			}
		});

	}
	
	private void initLayout(){
		vl.setWidth("100%");
		vl2.setWidth("100%");
		hl.setWidth("50%");
	}
	
	private void initForm(){
		
	}
	
	private void initTable(){
		Table table = new Table("My table");
		for (Meme meme : database){
			
		}
		
		vl2.addComponent(table);
		hl.addComponent(vl2);
	}

}
