package com.ylb.util.openfire;

import cm.software.im.openfire.utils.Log;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.util.PacketParserUtils;
import org.jivesoftware.smackx.disco.packet.DiscoverItems;
import org.jivesoftware.smackx.pubsub.*;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;
import org.jxmpp.jid.Jid;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class Subscriber implements ItemEventListener {
    private static XMPPTCPConnection connection = null;

    public Subscriber(XMPPTCPConnection con) {
        this.connection = con;
    }

    public boolean isSubscribed (String nodeId) {
        PubSubManager manager = PubSubManager.getInstance(connection);
        try {
            // Get self all the subscriptions in the service
            List<Subscription> subscriptions = manager.getSubscriptions();
            for (Subscription sub:subscriptions) {
                Jid jid = sub.getJid();
                String id = sub.getId();
                String nodeName = sub.getNode();
                Subscription.State state = sub.getState();
                Log.i("discoverSubscriptions",jid.toString() + "  has subscription id: " + id + " to node: " + nodeName + " state: "+sub.getState());
                if (nodeName.equals(nodeId) &&  state.equals(Subscription.State.subscribed)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void subscribe(String nodeId, String jid) {
        try {
            if (connection != null) {
                PubSubManager pubSubManager = PubSubManager.getInstance(connection);
                LeafNode eventNode = pubSubManager.getNode(nodeId);
                if (eventNode != null) {
                    eventNode.addItemEventListener(this);
                    //eventNode.subscribe(connection.getUser().toString());
                    if (jid != null && !isSubscribed(nodeId)) {
                        eventNode.subscribe(jid);
                    }
                }
            }
        } catch (InterruptedException | XMPPException | SmackException e) {
            e.printStackTrace();
        }
    }

    public void unsubscribe(String nodeId, String jid) {
        PubSubManager manager = PubSubManager.getInstance(connection);
        
        try {
            LeafNode node = manager.getNode(nodeId);
            if (node != null) {
                node.unsubscribe(jid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // not include item content
    public void getNodeItems(String nodeId) {
        PubSubManager manager = PubSubManager.getInstance(connection);
        try {
            DiscoverItems items = manager.discoverNodes(nodeId);
            Log.i("getNodeItems", "====> getItems size: "+items.getItems().size()+ " node: "+items.getNode());
            List<DiscoverItems.Item> itemList = items.getItems();
            for(DiscoverItems.Item item:itemList) {
                Log.i("getNodeItems", "====> item: "+item.toXML());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // include item content
    public void getNodeItems(String nodeId, int total) {
        PubSubManager manager = PubSubManager.getInstance(connection);
        try {
            // Get the node
            LeafNode node = manager.getNode(nodeId);
            if(node != null) {
                List<Item> items = node.getItems(total);
                for(Item item:items) {
                    Log.i("getNodeItem", item.toXML("").toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllSubscriptions() {
        PubSubManager manager = PubSubManager.getInstance(connection);
        try {
            // Get self all the subscriptions in the service
            List<Subscription> subscriptions = manager.getSubscriptions();
            for (Subscription sub:subscriptions) {
                Jid jid = sub.getJid();
                String id = sub.getId();
                String nodeNam = sub.getNode();
                Log.i("discoverSubscriptions",jid.toString() + "  has subscription id: " + id + " to node: " + nodeNam + " state: "+sub.getState());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void discoverSubscriptions(String nodeId) {
        PubSubManager manager = PubSubManager.getInstance(connection);
        try {
            Log.i("discoverSubscriptions", "service jid: "+ manager.getServiceJid().toString());
            ConfigureForm config = manager.getDefaultConfiguration();
            Log.i("discoverSubscriptions", "getAccessModel: "+ config.getAccessModel().name());
            Log.i("discoverSubscriptions", "getType: "+ config.getType().name());
//            Log.i("discoverSubscriptions", "getDataType: "+config.getDataType());
            Log.i("discoverSubscriptions", "getMaxItems: "+ config.getMaxItems());
//            Log.i("discoverSubscriptions", "getNodeType: "+config.getNodeType().name());
            Log.i("discoverSubscriptions", "getPublishModel: "+ config.getPublishModel().name());
//            Log.i("discoverSubscriptions", "getTitle: "+ config.getTitle());
//            Log.i("discoverSubscriptions", "getNotificationType: "+config.getNotificationType().name());
//            Log.i("discoverSubscriptions", "getChildrenAssociationWhitelist: "+config.getChildrenAssociationWhitelist().size());

            // Get the affiliations for the users bare JID
//            List<Affiliation> affiliations = manager.getAffiliations();
//            for(Affiliation aff:affiliations) {
//                Log.i("discoverSubscriptions", aff.toString());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handlePublishedItems(ItemPublishEvent itemPublishEvent) {
        for (Object obj : itemPublishEvent.getItems()) {
            Item  its = (Item)obj;
//            Log.i("handlePublishedItems", its.toString());
            PayloadItem item = (PayloadItem) obj;
            System.out.println("Payload: " + item.getPayload().toXML(""));
            try {
                XmlPullParser xmlParser = PacketParserUtils.getParserFor(item.getPayload().toXML("").toString());
                Message message = PacketParserUtils.parseMessage(xmlParser);
                System.out.println("subject: " + message.getSubject());
                System.out.println("body: " + message.getBody());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
