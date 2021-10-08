package com.csun.mall.service;

import com.csun.mall.domain.Message;
import com.csun.mall.domain.MessageRecord;
import com.csun.mall.domain.ProductCategory;
import com.csun.mall.mapper.MessageMapper;
import com.csun.mall.mapper.MessageRecordMapper;
import com.csun.mall.pojo.dto.MessageDTO;
import com.csun.mall.pojo.dto.MessageRO;
import com.csun.mall.web.response.PageParam;
import com.csun.mall.web.response.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author yanpanyi
 * @date 2019/4/4
 */
@Slf4j
@Service
public class MessageRecordService {

    @Resource
    private MessageRecordMapper messageRecordMapper;

    @Resource
    private MessageMapper messageMapper;

    public int addMessage(Message message){
        return messageMapper.insert(message);
    }

    public void addRecord(MessageRecord message) {
        messageRecordMapper.insert(message);
    }

    public void addRecord(MessageRO messageRO, Message message) {
        MessageRecord messageRecord = new MessageRecord();
        messageRecord.setMessageId(message.getId());
        messageRecord.setMessage(messageRO.getMessage());
        messageRecord.setType(messageRO.getType());
        messageRecord.setSendTime(messageRO.getSendTime());
        messageRecord.setReplyUserId(message.getReplyUserId());
        messageRecordMapper.insert(messageRecord);
    }

    public PageResult<MessageDTO> page(Long messageId, PageParam param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<MessageDTO> page = messageRecordMapper.page(messageId);
        return PageResult.from(page, MessageDTO.class);
    }
//
//    /**
//     * 创建文件名
//     *
//     * @return 文件名
//     */
//    private String createFileName() {
//        Calendar calendar = Calendar.getInstance();
//        StringBuffer sb = new StringBuffer();
//        sb.append(path);
//        sb.append(calendar.get(Calendar.YEAR));
//        sb.append(File.separator);
//        sb.append(calendar.get(Calendar.MONTH) + 1);
//        sb.append(File.separator);
//        sb.append(DateUtils.getDate(calendar.getTime(), DateConstant.CHAT_RECORD_FILE_NAME));
//        sb.append(FILE_SUFFIX);
//
//        return sb.toString();
//    }

    /**
     * 格式化内容
     *
     * @param chatRecordDTO 聊天记录对象
     * @return 格式化后的字符串
     */
//    private String formatContent(ChatRecordDTO chatRecordDTO) {
//        if (null == chatRecordDTO) {
//            return "";
//        }
//
//        StringBuffer sb = new StringBuffer();
//
//        User user = chatRecordDTO.getUser();
//        switch (chatRecordDTO.getType()) {
//            case ROBOT:
//            case USER:
//                formatUserMsg(sb, chatRecordDTO);
//                break;
//            case SYSTEM:
//                formatSystemMsg(sb, chatRecordDTO);
//                break;
//            case REVOKE:
//                chatRecordDTO.setMessage(user.getUsername() + "撤回了一条消息！");
//                formatSystemMsg(sb, chatRecordDTO);
//                break;
//            default:
//                break;
//        }
//
//        return sb.toString();
//    }
//
//    @Override
//    public List<HashMap<String, Object>> listRecord(String directoryName) {
//        File file = new File(path + directoryName);
//        if (!file.exists()) {
//            return null;
//        }
//
//        String[] tempList = file.list();
//        if (tempList == null || tempList.length < 1) {
//            return null;
//        }
//
//        List<HashMap<String, Object>> list = new ArrayList<>(tempList.length);
//        HashMap<String, Object> map;
//        String url = null;
//        for (String name : tempList) {
//            map = new HashMap<>(3, 1.0f);
//            // 是否是文件
//            boolean isFile = name.lastIndexOf(FILE_SUFFIX) != -1;
//            if (isFile) {
//                // 文件访问地址
//                url = accessAddress + directoryName + name;
//            }
//            map.put("name", name);
//            map.put("url", url);
//            map.put("file", isFile);
//
//            list.add(map);
//        }
//
//        return list;
//    }
//
//    /**
//     * 格式化系统类型的消息
//     *
//     * @param sb StringBuffer对象
//     * @param chatRecordDTO 聊天记录对象
//     */
//    private void formatSystemMsg(StringBuffer sb, ChatRecordDTO chatRecordDTO) {
//        sb.append("#### [");
//        sb.append(chatRecordDTO.getSendTime());
//        sb.append("] 系统消息：\r\n");
//        sb.append("> ");
//        sb.append(chatRecordDTO.getMessage());
//        sb.append("\r\n");
//    }
//
//    /**
//     * 格式化用户类型的消息
//     *
//     * @param sb StringBuffer对象
//     * @param chatRecordDTO 聊天记录对象
//     */
//    private void formatUserMsg(StringBuffer sb, ChatRecordDTO chatRecordDTO) {
//        final User user = chatRecordDTO.getUser();
//        String tag = chatRecordDTO.getType() == MessageTypeEnum.ROBOT ? "[系统机器人] " : "";
//        sb.append("#### [");
//        sb.append(chatRecordDTO.getSendTime());
//        sb.append("] ");
//        sb.append(tag);
//        sb.append(user.getUsername());
//        sb.append("(");
//        sb.append(user.getAddress());
//        sb.append(")：\r\n");
//
//        if (!StringUtils.isEmpty(chatRecordDTO.getImage())) {
//            sb.append("> ![](");
//            sb.append(chatRecordDTO.getImage());
//            sb.append(")\r\n");
//        }
//        if (!StringUtils.isEmpty(chatRecordDTO.getMessage())) {
//            sb.append("> ");
//            sb.append(StringEscapeUtils.escapeHtml4(chatRecordDTO.getMessage()));
//            sb.append("\r\n");
//        }
//    }
}
