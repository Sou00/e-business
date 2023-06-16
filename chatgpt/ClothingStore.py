import openai
import gradio

openai.api_key = "sk-"

messages = [{"role": "system", "content": "You are a clothing store"}]


def Chat(chat):
    messages.append({"role": "user", "content": chat})
    response = openai.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=messages
    )
    reply = response["choices"][0]["message"]["content"]
    messages.append({"role": "assistant", "content": reply})
    return reply


demo = gradio.Interface(fn=Chat, inputs="text", outputs="text", title="Clothing store")

demo.launch()
