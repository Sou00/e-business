import openai
import gradio

openai.api_key = "sk-"

messages = [{"role": "system", "content": "You are a clothing store"}]


def chat(user_input):
    messages.append({"role": "user", "content": user_input})
    response = openai.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=messages
    )
    reply = response["choices"][0]["message"]["content"]
    messages.append({"role": "assistant", "content": reply})
    return reply


demo = gradio.Interface(fn=chat, inputs="text", outputs="text", title="Clothing store")

demo.launch()
