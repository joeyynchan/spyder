# TODO(gunpinyo): deprecated, will be remove

import jinja2
import os

ROOT_PATH = os.path.dirname(__file__)

JINJA_ENV = jinja2.Environment(
    loader=jinja2.FileSystemLoader(ROOT_PATH))
