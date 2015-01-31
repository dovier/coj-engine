package cu.uci.uengine.runnable;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import cu.uci.coj.model.SubmissionJudge;
import cu.uci.uengine.EngineManager;
import cu.uci.uengine.utils.Utils;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SubmitRunner implements ChannelAwareMessageListener {

	static Log log = LogFactory.getLog(SubmitRunner.class.getName());

	@Resource
	private EngineManager engineManager;

	@Autowired
	private RabbitTemplate submitTemplate;

	@Resource
	private JsonMessageConverter jsonMessageConverter;

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {

		SubmissionJudge submit = (SubmissionJudge) jsonMessageConverter
				.fromMessage(message);
		submit.setVerdict(null);
		submit.setStatus(null);
		submit.setLanguage(Utils.getLanguages().get(submit.getLang()));

		log.info(String.format("Retrieved %s", submit.getSid()));
		log.info(String.format("Working on sid %s", submit.getSid()));

		SubmissionJudge result = engineManager.call(submit);

		Utils.setVerdict(result);

		log.info(String.format("Sending sid %s: %s (%sms)", submit.getSid(),
				submit.getVerdict(), submit.getTimeUsed()));

		submitTemplate.convertAndSend(result);

		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

		log.info(submit.toString());
	}

}
