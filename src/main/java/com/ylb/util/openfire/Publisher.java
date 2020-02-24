package com.ylb.util.openfire;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubException;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.jivesoftware.smackx.pubsub.SimplePayload;
import org.jivesoftware.smackx.xdata.packet.DataForm;

import cm.software.im.openfire.utils.Log;

public class Publisher {
	private static XMPPTCPConnection connection = null;
	private int sizeOfImageInBytes = 0;

	public Publisher(XMPPTCPConnection conn) {
		this.connection = conn;
	}

	private String encodeFileToBase64Binary(File file) {
		String encodedFile = null;
		try {
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			sizeOfImageInBytes = fileInputStreamReader.read(bytes);
			encodedFile = Base64.getEncoder().encodeToString(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return encodedFile;
	}

	public void createNode(String title, String description, String nodeId) {
		// Create a pubsub manager using an existing XMPPConnection
		PubSubManager manager = PubSubManager.getInstance(connection);

		LeafNode leafNode = null;
		try {
			leafNode = manager.getNode(nodeId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (leafNode == null) {
				// Create the node
				ConfigureForm form = new ConfigureForm(DataForm.Type.submit);
				form.setTitle(title);
//                form.setInstructions(description);
				form.setAccessModel(AccessModel.open); // anyone can access
				form.setDeliverPayloads(true); // allow payloads with notifications
				form.setNotifyRetract(true);
				form.setPersistentItems(true); // save published items in storage @ server
				form.setPresenceBasedDelivery(false); // notify subscribers even when they are offline
//                form.setPublishModel(PublishModel.open);
				form.setPublishModel(PublishModel.publishers); // only publishers (owner) can post items to this node
//                form.setAnswer("pubsub#description", description);
//                form.setAnswer("pubsub#item_expire", 604800);
//                form.setAnswer("pubsub#send_last_published_item", true);
				leafNode = (LeafNode) manager.createNode(nodeId, form);
			}
		} catch (SmackException.NoResponseException e) {
			e.printStackTrace();
		} catch (XMPPException.XMPPErrorException e) {
			e.printStackTrace();
		} catch (SmackException.NotConnectedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void deleteNode(String nodeId) {
		PubSubManager manager = PubSubManager.getInstance(connection);
		try {
			manager.deleteNode(nodeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SimplePayload createPayload(String subject, String msg) {
		Message message = new Message();
		message.setStanzaId();
		message.setSubject(subject);
		message.setBody(msg);
		SimplePayload payload = new SimplePayload(message.toXML("").toString());
		return payload;
	}

	public void publishItem(String nodeId, String itemId, SimplePayload payload) {
		PubSubManager manager = PubSubManager.getInstance(connection);

		try {
			LeafNode leafNode = manager.getNode(nodeId);
			if (leafNode != null) {
				PayloadItem<SimplePayload> item = new PayloadItem<>(itemId, payload);
				leafNode.publish(item);
			}
		} catch (SmackException.NoResponseException e) {
			e.printStackTrace();
		} catch (XMPPException.XMPPErrorException e) {
			e.printStackTrace();
		} catch (SmackException.NotConnectedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (PubSubException.NotAPubSubNodeException e) {
			e.printStackTrace();
		}
	}

	public void publishItem(String nodeId, String itemId) {
		PubSubManager manager = PubSubManager.getInstance(connection);

		try {
			LeafNode leafNode = manager.getNode(nodeId);
			if (leafNode != null) {
				// Publish an Item with payload
//                node.send(new PayloadItem("test" + System.currentTimeMillis(),
//                        new SimplePayload("book", "pubsub:test:book", "Two Towers")));

//                String msg = "["+ TimeInfo.localTime() + "] pub-sub node item test mesage";
//                SimplePayload payload = new SimplePayload("message","pubsub:test:message", "<message xmlns='pubsub:test:message'><body>"+msg+"</body></message>");
//                PayloadItem<SimplePayload> item = new PayloadItem<SimplePayload>(itemId, payload);
//                leafNode.publish(item);

				String msg = "<studentcouncil@strathmore.edu>\n" + "\t\n" + "\t\n" + "to AllStudents\n"
						+ "Good afternoon Stratizens,\n" + "\n"
						+ "Make your way to the auditorium at 2pm for an event that SUITSA is holding an information security forum.\n"
						+ "\n" + "Attached is the poster for more details.\n"
						+ "Register now to attend here: http://bit.ly/infosecday2018\n" + "Kind Regards,";

//                String filepath = "/home/john/Pictures/unnamed.jpg";
//                File file = new File(filepath);
//                String imageBase64 = encodeFileToBase64Binary(file);
//                StandardExtensionElement extFileNameBuilder = StandardExtensionElement.builder(
//                        "file", "jabber:client")
//                        .addElement("base64Bin", imageBase64)
//                        .addAttribute("name", file.getName())
//                        .addAttribute("size", "" + sizeOfImageInBytes)
//                        .build();

				Message message = new Message();
				message.setStanzaId();
				message.setSubject("Student Council");
				message.setBody(msg);
//                message.addExtension(extFileNameBuilder);

				// String xmlMsg = "<message xmlns='pubsub:test:test'>" + msg + "</message>";
				SimplePayload payload = new SimplePayload(message.toXML("").toString());
				PayloadItem<SimplePayload> item = new PayloadItem<>(itemId, payload);
				leafNode.publish(item);
			}
		} catch (SmackException.NoResponseException e) {
			e.printStackTrace();
		} catch (XMPPException.XMPPErrorException e) {
			e.printStackTrace();
		} catch (SmackException.NotConnectedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (PubSubException.NotAPubSubNodeException e) {
			e.printStackTrace();
		}
	}

	public void deleteItem(String nodeId, String itemId) {
		PubSubManager manager = PubSubManager.getInstance(connection);
		try {
			LeafNode node = manager.getNode(nodeId);
			if (node != null) {
				node.deleteItem(itemId);
			}
		} catch (SmackException.NoResponseException e) {
			e.printStackTrace();
		} catch (XMPPException.XMPPErrorException e) {
			e.printStackTrace();
		} catch (SmackException.NotConnectedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (PubSubException.NotAPubSubNodeException e) {
			e.printStackTrace();
		}
	}

	public void getNodeConfig(String nodeId) {
		PubSubManager manager = PubSubManager.getInstance(connection);
		try {
			// Get the node
			LeafNode node = manager.getNode(nodeId);
			if (node != null) {
				ConfigureForm config = node.getNodeConfiguration();
				Log.i("getNodeConfig", "getTitle : " + config.getTitle());
				Log.i("getNodeConfig",
						"description : " + config.getField("pubsub#description").getValuesAsString().toString());
				Log.i("getNodeConfig", "getInstructions : " + config.getInstructions());
				Log.i("getNodeConfig", "getType : " + config.getType());
				Log.i("getNodeConfig", "getNodeType : " + config.getNodeType());
				Log.i("getNodeConfig", "getDataType : " + config.getDataType());
//                Log.i("getNodeConfig", "getNotificationType : " + config.getNotificationType().name());
				Log.i("getNodeConfig", "getPublishModel : " + config.getPublishModel());
				Log.i("getNodeConfig", "getAccessModel : " + config.getAccessModel());
				Log.i("getNodeConfig", "getCollection : " + config.getCollection());
				Log.i("getNodeConfig", "getMaxItems : " + config.getMaxItems());

			}
		} catch (SmackException.NoResponseException e) {
			e.printStackTrace();
		} catch (XMPPException.XMPPErrorException e) {
			e.printStackTrace();
		} catch (SmackException.NotConnectedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (PubSubException.NotAPubSubNodeException e) {
			e.printStackTrace();
		}
	}

	public void getNodeItem(String nodeId, int total) {
		PubSubManager manager = PubSubManager.getInstance(connection);

//        // Get the node information
//        DiscoverInfo nodeInfo = node.discoverInfo();
//        // Discover the node items
//        DiscoverItems nodeItems = node.discoverItems();
//        // Discover the node subscriptions
//        List<Subscription> subscriptions = node.getSubscriptions();

		try {
			// Get the node
			LeafNode node = manager.getNode(nodeId);
			if (node != null) {
				List<Item> items = node.getItems(total);
				for (Item item : items) {
					Log.i("getNodeItem", item.toString());
				}
			}
		} catch (SmackException.NoResponseException e) {
			e.printStackTrace();
		} catch (XMPPException.XMPPErrorException e) {
			e.printStackTrace();
		} catch (SmackException.NotConnectedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (PubSubException.NotAPubSubNodeException e) {
			e.printStackTrace();
		}
	}
}
