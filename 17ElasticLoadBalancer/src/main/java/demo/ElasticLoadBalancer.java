package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * @author Remi SHARROCK and Axel Mathieu
 * @description Create an actor and passing his reference to
 *				another actor by message.
 */
public class ElasticLoadBalancer {

	public static void main(String[] args) {
		// Instantiate an actor system
		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first and second actor
		final ActorRef balancer = system.actorOf(LoadBalancerActor.createActor(), "balancer");
		final ActorRef a = system.actorOf(SenderActor.createActor(), "a");

		MessageRef ref = new MessageRef(balancer);
		a.tell(ref, ActorRef.noSender());
		
		MessageNumberBalancer m = new MessageNumberBalancer(2);
		balancer.tell(m, ActorRef.noSender());
	
	
	    // We wait 5 seconds before ending system (by default)
	    // But this is not the best solution.
	    try {
			waitBeforeTerminate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}
	}

	public static void waitBeforeTerminate() throws InterruptedException {
		Thread.sleep(10000);
	}

}
