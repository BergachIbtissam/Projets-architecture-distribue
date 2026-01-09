package agents;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ElectionBehaviour extends CyclicBehaviour {

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();

        if (msg != null) {
            if ("ELECTION".equals(msg.getContent())) {
                handleElection(msg);
            }
        } else {
            block();
        }
    }

    private void handleElection(ACLMessage msg) {
        int senderId = Integer.parseInt(
                msg.getSender().getLocalName().replaceAll("\\D", "")
        );

        CoordinatorAgent agent = (CoordinatorAgent) myAgent;

        if (agent.getId() > senderId) {
            System.out.println(myAgent.getLocalName() +
                    " : ID plus grand, je démarre une élection");

            agent.startElection();
        }
    }
}
