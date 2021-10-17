import * as React from "react";
import { useSelector } from "react-redux";
import AppModel from "../../model/AppModel";
import Constants from "../../util/Constants";
import Message from "./Message";
import "./Messages.scss";

export const Messages: React.FC = () => {
  const messages = useSelector((state: AppModel) => state.messages);
  const count =
    messages.length < Constants.MESSAGE_DISPLAY_COUNT
      ? messages.length
      : Constants.MESSAGE_DISPLAY_COUNT;
  const toRender = messages.slice(0, count);
  return (
    <div className={"message-container messages-" + count}>
      {toRender.map((m) => (
        <Message key={m.timestamp!} message={m} />
      ))}
    </div>
  );
};

export default Messages;
