/**
 * MIT License
 * <p>
 * Copyright (c) 2016 - 2019 Silvio Wangler (silvio.wangler@gmail.com)
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ch.silviowangler.rest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Silvio Wangler
 */
public class EntityModel implements Serializable {

    private ResourceModel data;
    private List<ResourceLink> links;

	public EntityModel() {
		this(null);
	}

	public EntityModel(ResourceModel data) {
		this(data, new ArrayList<>());
	}

	public EntityModel(ResourceModel data, List<ResourceLink> links) {
		this.data = data;
		this.links = links;
	}

	public ResourceModel getData() {
        return data;
    }

    public void setData(ResourceModel data) {
        this.data = data;
    }

    public List<ResourceLink> getLinks() {
        return links;
    }

    public void setLinks(List<ResourceLink> links) {
        this.links = links;
    }
}
