package com.project.memecollector;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Title;
import com.vaadin.data.Item;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Title("Main App")
@Push
public class Main extends UI implements Broadcaster.BroadcastListener {
	
	private static final long serialVersionUID = 1L;
	
	private List<Meme> database = new ArrayList<Meme>();
	private Long memeId = (long)0;
	private VerticalLayout vl = new VerticalLayout();
	private VerticalLayout vl2 = new VerticalLayout();
	private HorizontalLayout hl = new HorizontalLayout();
	private Table table = new Table("Meme Collection");
	
	
	@Override
	protected void init(VaadinRequest request) {
		initLayout();
		initForm();
		initTable();
		setContent(hl);
		Broadcaster.register(this);
	}
	
	private void initLayout(){
		vl.setWidth("100%");
		vl2.setWidth("100%");
		hl.setWidth("50%");
	}
	
	private void initForm(){
		final TextField name = new TextField("Meme name");
		final TextField url = new TextField("Link to meme");
		Button submitButton = new Button("Submit!");
		
		submitButton.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				Meme newMeme = new Meme(memeId++,name.getValue(),url.getValue());
				database.add(newMeme);
				new Notification("This is a warning",
					    "" + database.size(),
					    Notification.TYPE_WARNING_MESSAGE, true)
					    .show(Page.getCurrent());
				Broadcaster.broadcast(newMeme);
			}
			
		});
		
		vl.addComponent(name);
		vl.addComponent(url);
		vl.addComponent(submitButton);
		hl.addComponent(vl);
	}
	
	private void initTable(){
		table.addContainerProperty("name", String.class, null);
		table.addContainerProperty("url", Link.class, null);
		table.addContainerProperty("action", Button.class, null);
		for (final Meme meme : database){
			Item item = table.addItem(meme.getId());
			item.getItemProperty("name").setValue(meme.getTitle());
			Link tmp = new Link("", new ExternalResource(meme.getLinkToMeme()));
			tmp.setIcon(new ExternalResource(meme.getLinkToMeme()));
			item.getItemProperty("url").setValue(tmp);
			Button deleteButton = new Button("Delete");
			deleteButton.addClickListener(new ClickListener(){

				@Override
				public void buttonClick(ClickEvent event) {
					database.remove(meme);
				}
				
			});
			item.getItemProperty("action").setValue(deleteButton);
		}
		
		vl2.addComponent(table);
		hl.addComponent(vl2);
	}
	
	@Override
	public void detach(){
		Broadcaster.unregister(this);
		super.detach();
	}

	@Override
	public void receiveBroadcast(final Meme meme) {
		// TODO Auto-generated method stub
		access(new Runnable(){

			@Override
			public void run() {
				Item item = table.addItem(meme.getId());
				item.getItemProperty("name").setValue(meme.getTitle());
				Link tmp = new Link("", new ExternalResource(meme.getLinkToMeme()));
				tmp.setIcon(new ExternalResource(meme.getLinkToMeme()));
				item.getItemProperty("url").setValue(tmp);
				Button deleteButton = new Button("Delete");
				deleteButton.addClickListener(new ClickListener(){

					@Override
					public void buttonClick(ClickEvent event) {
						database.remove(meme);
					}
					
				});
				item.getItemProperty("action").setValue(deleteButton);
			}
			
		});
	}

}
